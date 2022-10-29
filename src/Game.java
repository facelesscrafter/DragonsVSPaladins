import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Game {
    private static Scanner scanner=new Scanner(System.in);
    private static final String savePath="save.txt";
    private static final String key="key";
    private static final String navigation="Куда вы хотите пойти?\n " +
            "1. К Торговцу\n " +
            "2. В темный лес\n " +
            "Q. Выход";

    private static Battle battle = null;
    private static Character player=null;
    private static String side;
    interface FightCallback {
        void fightWin();
        void fightLost();
    }

    static void initGame() {
        battle=new Battle();
        String c=null;
        int code=0;
        if(searchSave()){
            System.out.println("Найдено сохранение.");
            while (code==0) {
                c = readCommand("\n 1. Загрузить сохранение?\n 2. Начать новую игру\n Q. Выйти" +
                        "\n\nВведите 1, 2 или q");
                if(c.matches("^[1,2]+$"))code=Integer.parseInt(c);
                else System.out.println("Неверная команда.");
            }
            if(code==1 )loadSave();
            if(player!=null)
                System.out.println("Продолжает свой великий поход "+player.getSummaryStats() );

        }
        if(code==2 || code==0 || player==null) {
            code=0;
            while (code==0) {
                c = readCommand("Выберите сторону:\n 1. Рыцари\n 2. Драконы\n Q. Выйти" +
                        "\n\nВведите 1, 2 или q");
                if(c.matches("^[1,2]+$"))code=Integer.parseInt(c);
                else System.out.println("Неверная команда.");
            }
            if(code==1) {
                player = new Paladin(readCommand("Введите имя персонажа. Q - выйти"), 100, 20, 10, 0, 0);
                System.out.println("\"...\n" +
                        "Собрали мы рейд в цлк.\n" +
                        "Паладин у нас танчил опять.\n" +
                        "Сагрил он кучу мобов,\n" +
                        "И стали они всем давать\n\n" +
                        "Когда отвалился весь рейд,\n" +
                        "Остался один паладин.\n" +
                        "Он спулил Лича случайно...\n" +
                        "Одним щитом загасил.\n\n" +
                        "И чтобы я не делал - или танчил или хилил:\n" +
                        "Дпса нету круче в этом мире!\n" +
                        "Я передамажу даже адского дд!\n" +
                        "Нажимая при этом кнопки две...\n\"");
                System.out.println("В мире стало на одного будущего великого паладина больше! И имя ему " + player.getName());
                side="paladins";
            }
            else {
                player = new Dragon(readCommand("Введите имя персонажа. Q - выйти"), 100, 20, 10, 15, 10,0,0,"green");
                System.out.println("DRAGONS ARE AWESOME!!!\n" +
                        "Многие тысячилетия алчное людское племя уничтожает наших собратьев.\n" +
                        "Мы стали селиться в самых удаленных и негостеприимных местах, но и там нас находят!\n" +
                        "Мало того, что ни у кого из нас не осталось даже маленькой плешивой медной монеты, так перед смертью многие собратья вынуждены слышать унизительное \"И это весь лут?\"" +
                        "\nМаленький зеленый дракончик, коротки твои зимы, не окрепли ещё твои крылья, но мы верим в тебя и твою миссию!" +
                        "\nДРАКАРИС!!!");
                System.out.println("Войну треклятым паладинам объявил зеленый дракон " + player.getName());
                side="dragons";
            }
        }
    }
    public static String getSide() {
        return side;
    }
    //максимально контролируем ввод
    static String readCommand(String message){
        String command=null;
        System.out.println(message);
            try {
                command=scanner.nextLine();
            } catch (NullPointerException | NoSuchElementException e) {
                System.out.println("Вы ввели что-то уж совсем недопустимое. Игра будет завершена. Пытаемся сохранить прогресс");
                if(player!=null)saveGame();
                System.exit(1);

            }
        return command;
    }
    public static void play(String string) {
        switch (string.toLowerCase()) {
            case "q":{
                if(player!=null) {
                    string = readCommand("Сохранить игру?\n 1. Да.\n 2. Любой иной набор символов - нет");
                    if (string.equals("1") || string.toLowerCase().equals("да")) saveGame();
                }
                System.exit(1);
            }
            break;
            case "1": {
                System.out.println("Торговец еще не приехал");
            }
            break;
            case "2":
                commitFight();
            break;
            case "да":
                play("2");
                break;
        }
        play(readCommand(navigation));
    }
    private static void commitFight() {
        battle.fight(player, createMonster(), new FightCallback() {
            @Override
            public void fightWin() {
                System.out.println(String.format("%s победил! Теперь у вас %d опыта и %d золота, а также осталось %d едениц здоровья.", player.getName(), player.getXp(), player.getGold(), player.getHp()));
                System.out.println("Желаете продолжить поход или вернуться в город? (да/нет)");
            }

            @Override
            public void fightLost() {

            }
        },side);

    }
    private static Character createMonster() {
        int random = (int) (Math.random() * 10);
        if (random % 2 == 0) return new Goblin(
                "Гоблин",
                50,
                10,
                10,
                100,
                20
        );
        else return new Skeleton(
                "Скелет",
                25,
                20,
                20,
                100,
                10
        );
    }
    static boolean searchSave(){
        File save = new File(savePath);
        return save.exists();
    }

    private static void saveGame()   {
        //сохранение игры
        String data = player.getStats();
        byte[] dataB=data.getBytes();
        byte[] keyB=key.getBytes();

        try{
            //без бэкапов, только хардкор =) Dark Souls нервно курит в сторонке
            //никуда без простенького шифрования статов нашего героя
            for(int i=0;i< dataB.length;i++)dataB[i]=(byte) (dataB[i] ^ keyB[i % keyB.length]) ;
            //поддержим старенькие java!
            Writer writeSave = new OutputStreamWriter(new FileOutputStream(savePath), "Cp1251");
            writeSave.write(player.getName()+"#"+new String(dataB));
            writeSave.close();

        } catch (IOException e) {
            System.out.println("Ошибка сохранения... Увы, ВЕСЬ прогресс утерян."); // =) ох уж эти злые разрабы
            System.exit(1);
        }
        System.out.println("Игра успешно сохранена.");
    }
    private static void loadSave(){
        String text="";
        String name;
        Reader save;
        String[] stats;
        int c;
        try {
            //на всякий будем поддерживать java младших версий
            save = new InputStreamReader(new FileInputStream(savePath), "Cp1251");
            while ((c = save.read()) != -1) {
                text=text+(char) c;
            }
            save.close();
            if(text.contains("#")) {
                name = text.substring(0, text.indexOf("#"));
                text = text.substring(text.indexOf("#") + 1, text.length());
                byte[] dataB = text.getBytes();
                byte[] keyB = key.getBytes();
                for (int i = 0; i < dataB.length; i++) dataB[i] = (byte) (dataB[i] ^ keyB[i % keyB.length]);
                text = new String(dataB);
                stats = text.split("\n");
                if(stats.length==5){
                    player = new Paladin(name, Integer.parseInt(stats[0]), Integer.parseInt(stats[1]), Integer.parseInt(stats[2]), Integer.parseInt(stats[3]), Integer.parseInt(stats[4]));
                    side = "paladins";
                }
                else if(stats.length==8){
                    player=new Dragon(name, Integer.parseInt(stats[0]), Integer.parseInt(stats[1]), Integer.parseInt(stats[2]), Integer.parseInt(stats[3]), Integer.parseInt(stats[4]), Integer.parseInt(stats[5]), Integer.parseInt(stats[6]), stats[7]);
                    side="dragons";
                }
                else System.out.print("Файл сохранения поврежден. Начинаем новую игру");
            }
            else System.out.print("Файл сохранения поврежден. Начинаем новую игру\n");
        } catch (IOException e) {
            System.out.print("Не удалось загрузить сохранение. Начинаем новую игру\n");
        }

    }
    public static void main(String[] args)  {
        //инициализация игры
        initGame();
        play("start");

    }
}

