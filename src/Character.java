public abstract class Character implements Fighter{
    private String name;
    private int hp;
    private int str;
    private int dex;
    private int xp;
    private int gold;

    @Override
    public int attack() {
        if (dex * 3 > getRandom()) return str;
        else return  0;
    }

    private int getRandom() {
        return (int) (Math.random() * 100);
    }
    @Override
    public String toString() {
        return String.format("%s здоровье:%d", name, hp);
    }
    public Character(String name, int hp, int str, int dex, int xp, int gold) {
        this.name = name;
        this.hp = hp;
        this.str = str;
        this.dex = dex;
        this.xp = xp;
        this.gold = gold;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getStr() {
        return str;
    }

    public int getDex() {
        return dex;
    }

    public int getXp() {
        return xp;
    }

    public int getGold() {
        return gold;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public void setDex(int dex) {
        this.dex = dex;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
    public String getSummaryStats(){
        return "Персонаж "+this.getName()+"\n"+
                "Характеристики:\n" +
                "Здоровье: "+this.getHp()+"\n" +
                "Сила: "+this.getStr()+"\n" +
                "Ловкость: "+this.getDex()+"\n" +
                "Опыт: "+this.getXp()+"\n" +
                "Золото: "+this.getGold()+"\n";
    }
    public String getStats() {
        return this.getHp()+"\n" +
                this.getStr()+"\n" +
                this.getDex()+"\n" +
                this.getXp()+"\n" +
                this.getGold();
    }
}
