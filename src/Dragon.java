public class Dragon extends Character{
    private String color;   //цвет дракона
    private int fireStr; //сила огня, а str для Драконов - удар когтями (базовая атака)
    private int tailStr;
    @Override
    public String getStats() {
        return this.getHp()+"\n" +
                this.getStr()+"\n" +
                this.getFireStr()+"\n" +
                this.getTailStr()+"\n" +
                this.getDex()+"\n" +
                this.getXp()+"\n" +
                this.getGold() +"\n" +
                this.getColor();

    }
    @Override
    public String getSummaryStats(){
        return "Дракон "+this.getName()+"\n"+
                "Характеристики:\n" +
                "Здоровье: "+this.getHp()+"\n" +
                "Сила удара когтями: "+this.getStr()+"\n" +
                "Сила дыхания огнем: "+this.getFireStr()+"\n" +
                "Сила удара хвостом: "+this.getTailStr()+"\n" +
                "Ловкость: "+this.getDex()+"\n" +
                "Опыт: "+this.getXp()+"\n" +
                "Золото: "+this.getGold()+"\n";
    }
    public Dragon(String name, int hp, int str, int fireStr, int tailStr, int dex, int xp, int gold, String color) {
        super(name, hp, str, dex, xp, gold);
        this.fireStr=fireStr;
        this.tailStr=tailStr;
        this.color=color;
    }


    public String getColor() {
        return color;
    }

    public int getFireStr() {
        return fireStr;
    }

    public int getTailStr() {
        return tailStr;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setFireStr(int fireStr) {
        this.fireStr = fireStr;
    }

    public void setTailStr(int tailStr) {
        this.tailStr = tailStr;
    }
}
