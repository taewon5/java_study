package com.company;


import java.io.*;
import java.net.*;
import java.util.*;

class Sutda{
    CardCase cc = new CardCase();
    CardRule rule = new CardRule();
    Card[] cc1=new Card[CardUtil.SUTDA];
    Card[] cc2=new Card[CardUtil.SUTDA];

    public Sutda(){
        cc.make();
    }
    public void divide(){
        for(int i=0;i<CardUtil.SUTDA;i++){
            cc1[i]=cc.getCard(i);
            cc2[i]=cc.getCard(i+CardUtil.SUTDA);
        }
    }
    public void divide2(){
        for(int i=0,j=0;i<cc1.length;i++,j+=2){
            cc1[i]=cc.getCard(j);
            cc2[i]=cc.getCard(j+1);
        }
    }
    public void play(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("!!WELCOME TO CARDGAME"+new Date()+"!!");
        System.out.println("Start!");
        String ss="N";
        do {
            cc.shuffle();
            divide2();
            int v1 = rule.rule(cc1[0], cc1[1]);
            int v2 = rule.rule(cc2[0], cc2[1]);
            if (v1 > v2) {
                System.out.println("You WIn");
            } else if (v1 < v2) {
                System.out.println("You Lose");
            } else {
                System.out.println("You Same");
            }
            cc.print();
            System.out.println("You");
            System.out.print(cc1[0] + " " + cc1[1]);
            System.out.println(rule.rule(cc1[0], cc1[1]));
            System.out.println("Com");
            System.out.print(cc2[0] + " " + cc2[1]);
            System.out.println(rule.rule(cc2[0], cc2[1]));
            System.out.println("!!Welcome" + new Date() + "!!");
            System.out.println("Do you want to replay again?(Y/N");
            ss = scanner.next();
        }while((ss.toUpperCase()).equalsIgnoreCase("Y"));
        System.out.println("Good Bye");
    }
}
class CardRule{
    public int toV(Card c){
        int count=0;
        switch (c.getCardVal().charAt(1)){
            case 'A':count=1;break;
            case 'T':count=10;break;
            default:count=c.getCardVal().charAt(1)-'0';break;
        }
        return count;
    }
    public boolean isLight(Card c){
        boolean isL=false;
        if(c.getCardVal().charAt(0)=='C'){
            isL=true;
        }
        return isL;
    }
    private boolean is138(Card c){
        boolean isC=false;
        if(isLight(c)){
            if(c.getCardVal().charAt(1)=='1'||
            c.getCardVal().charAt(1)=='3'||
            c.getCardVal().charAt(1)=='8'){
                isC=true;
            }
        }
        return isC;
    }
    public int rule(Card c1,Card c2){
        int count=0;
        if(is138(c1)&&is138(c2)){
            if((toV(c1)*toV(c2)==24)&&(toV(c1)+toV(c2)==11)){
                count=3000;
            }else if((toV(c1)*toV(c2)==3)&&(toV(c1)+toV(c2)==4)){
                count=2000;
            }else if((toV(c1)*toV(c2)==8)&&(toV(c1)+toV(c2)==9)){
                count=1000;
            }
        }else {
            if((toV(c1)==toV(c2))){
                count=toV(c1)*100;
            }else {
                if((toV(c1)*toV(c2)==2)&&(toV(c1)+toV(c2)==3)){
                    count=99;
                }else if((toV(c1)*toV(c2)==4)&&(toV(c1)+toV(c2)==5)){
                    count=98;
                }else if((toV(c1)*toV(c2)==9)&&(toV(c1)+toV(c2)==10)){
                    count=97;
                }else if((toV(c1)*toV(c2)==10)&&(toV(c1)+toV(c2)==11)){
                    count=96;
                }else if((toV(c1)*toV(c2)==40)&&(toV(c1)+toV(c2)==14)){
                    count=95;
                }else if((toV(c1)*toV(c2)==24)&&(toV(c1)+toV(c2)==10)){
                    count=94;
                }else{
                    count=((toV(c1)+toV(c2))%10)*10;
                }
            }
        }
        return count;
    }
}
class CardComp implements Comparator<Card>{
    @Override
    public int compare(Card c1, Card c2) {
        if(c1.getCardVal().charAt(0)>c2.getCardVal().charAt(0)){
            return 1;
        }else if(c1.getCardVal().charAt(0)<c2.getCardVal().charAt(0)) {
            return -1;
        }else{
            if(CardUtil.toVal(c1.getCardVal().charAt(1))>
            CardUtil.toVal(c2.getCardVal().charAt(1))){
                return 1;
            }else if(CardUtil.toVal(c1.getCardVal().charAt(1))<
            CardUtil.toVal(c2.getCardVal().charAt(1))){
                return -1;
            }else{
                return 0;
            }
        }
    }
}
class CardCase{
    private List<Card>cards=new ArrayList<Card>();
    public CardCase(){
        cards.clear();
    }
    public List<Card> getCards(){
        return cards;
    }
    public int count(){
        return cards.size();
    }
    public Card getCard(int index){
        return cards.get(index);
    }
    public void make(){
        cards.clear();
        int suit=CardUtil.SUIT.length;
        int valu=CardUtil.VALU.length;
        int count = 0;
        while(count!=valu*suit){
            Card c = new Card();
            if(!cards.contains(c)){
                cards.add(c);
                count++;
            }
        }
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }
    public void print(){
        int value=CardUtil.VALU.length;
        for(int i=0;i<cards.size();i++){
            Card c = cards.get(i);
            System.out.printf("%s",c.toString());
            if((i+1)%value==0){
                System.out.println();
            }
        }
    }
    public void sort(){
        cards.sort(new CardComp());
    }
    public void rsort(){
        cards.sort(new CardComp().reversed());
    }
}
class CardUtil{
    public static final int SUTDA=2;
    public static final String[] SUIT={"H","C"};
    public static final String[] VALU={"A","2","3","4","5","6","7","8","9","T"};

    public static int toVal(Card c){
        return toVal(c.getCardVal().charAt(1));
    }
    public static int toVal(Card c,int index){
        return toVal(c.getCardVal().charAt(index));
    }
    public static int toVal(char cc){
        int tot=0;
        switch (cc){
            case 'A':tot=1;break;
            case 'T':tot=10;break;
            case 'J':tot=11;break;
            case 'Q':tot=12;break;
            case 'K':tot=13;break;
            default:tot=cc-'0';break;
        }
        return tot;
    }
}
class Card{
    private String cardVal;

    public String getCardVal() {
        return cardVal;
    }
    public Card(Card c){ //복사생성자
        this(c.getCardVal());
    }
    public Card(String cardVal) {
        this.cardVal = cardVal;
    }
    public Card(){
        int suit=(int)(Math.random()*CardUtil.SUIT.length);
        int valu=(int)(Math.random()*CardUtil.VALU.length);
        cardVal=CardUtil.SUIT[suit]+CardUtil.VALU[valu];
    }

    @Override
    public String toString() {
        return "["+cardVal+"]";
    }

    @Override
    public int hashCode() {
        final int prime=31;
        int result =1;
        result=prime*result+((cardVal==null)?0:cardVal.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Card c = (Card) obj;
        if(cardVal.equals(c.getCardVal())){
            return true;
        }else{
            return false;
        }
    }
}
public class Main {
    public static void main(String[] args)  {
        Sutda sutda = new Sutda();
        sutda.play();
    }
}






