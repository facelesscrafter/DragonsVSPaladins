public class Paladin extends Character{
    @Override
    public String getStats() {
        return this.getHp()+"\n" +
                this.getStr()+"\n" +
                this.getDex()+"\n" +
                this.getXp()+"\n" +
                this.getGold();
    }
    @Override
    public String getSummaryStats(){
        return "Паладин "+this.getName()+"\n"+
                "Характеристики:\n" +
                "Здоровье: "+this.getHp()+"\n" +
                "Сила: "+this.getStr()+"\n" +
                "Ловкость: "+this.getDex()+"\n" +
                "Опыт: "+this.getXp()+"\n" +
                "Золото: "+this.getGold()+"\n";
    }
    public Paladin(String name, int hp, int str, int dex, int xp, int gold) {
        super(name, hp, str, dex, xp, gold);
    }
}
