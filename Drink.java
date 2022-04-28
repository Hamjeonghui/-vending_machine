package team;

import java.util.Scanner;

class Drink{ //상위클래스

   String name;  // 음료수 이름
   private int pk; //(사용자와 관리자 둘 다 조작x)
   int cnt; //재고 개수 
   final int min=0; //음료 당 재고 개수 최소값
   final int max=10; //음료 당 재고 개수 최대값
   int price; //음료 가격

   public int getPk() {
      return pk;
   }

   public void setPk(int pk) {
      this.pk = pk;
   }

   Drink(String name, int cnt, int price){ //이름 , 재고, 가격
      this.name = name;               
      this.cnt = cnt;
      this.price=price;
   }   

   public String toString() { 
      return "["+this.name+"|"+this.pk+ "|"+ this.cnt+ "개|" + this.price + "원]\n";
   }
   // syso(객체);를 그냥 하게 된다면 -> syso(객체.toString)과 같은 뜻인데,
   // toString()은 "객체가 가지고 있는 정보나 값을 문자열로 만들어 리턴"해주는 메소드이다.
   // 그래서 오버라이딩을 통해 "이름,pk,재고"를 리턴해주게 재정의하였다!

}

class Coffee extends Drink{
   static int pkNum = 1001;
   Coffee(String name, int cnt, int price) { 
      super(name, cnt, price);
      this.setPk(pkNum++);

   }
}

class Sports extends Drink{
   static int pkNum = 2001;
   Sports(String name, int cnt, int price) {
      super(name, cnt, price);
      this.setPk(pkNum++);
   }
}

class Tea extends Drink{
   static int pkNum = 3001;
   Tea(String name, int cnt, int price) {
      super(name, cnt, price);
      this.setPk(pkNum++);
   }
}

public class Team01{
   static Scanner sc = new Scanner(System.in);
   static boolean check=false;
   static int money;
   
   //0.메인화면
   static boolean mainScreen() {
      check=false;//check를 false로 초기화
      String ans;

      System.out.println("구매를 진행하시려면 아무 버튼이나 입력해주세요.\n"
            + " [ 구매종료(거스름돈 받기) : n / 관리자모드 : **** ]");
      ans=sc.next();
      ans=ans.toLowerCase();
      

      if(ans.equals("n")) {

         System.out.println("구매를 종료합니다.");

         if(money>0) {//잔액이 있다면 거스름돈을 보여준다. -> 없다면 안보여준다.
            System.out.println("거스름돈 : "+money+"원");
         }

         return true; //종료하고 거스름돈을 출력하겠습니다.
      }

      else if(ans.equals("1234")){ //시크릿 코드 입력
         check=true;//관리자모드로 전환

      }

      return false;//종료하지 않고 구매모드 혹은 관리자모드로 진행하겠습니다.
   }

   //1.금액입력
   static void inputMoney() {
      final int min=500; //최저 투입금액
      int moneyInput; //입력받은 돈

      do {
         System.out.println("금액을 투입하세요   ["+money+"]");


         if(money>=min) {
            System.out.println("(추가 금액 없이 진행하시려면 0입력)");
         }

         moneyInput=sc.nextInt();

         if(moneyInput<0) { // 음수를 입력하면
            System.out.println("장난치지 마세요");
            continue;
         }

         money+=moneyInput;                           
      }while(money<min);

      return;

   }

   //2.구매모드(사용자화면 출력, 구매할 음료 선택, 유효성검사)
   static void buyMode(Drink[] drink) {
      check=false;//check를 false로 초기화
      int userChoice;//사용자화면에서의 입력
      System.out.println("★제품을 선택하세요★"+"[잔액 : "+money +"원]");

      for(int i=0; i<drink.length; i++) {
         System.out.print((i+1) + "." + drink[i].name+"["+drink[i].price+"원]");
         show(drink[i]);
      }

      userChoice = sc.nextInt();

      if(userChoice>=1&&userChoice<=6) { //사용자의 입력값이 존재하면

         buy(drink[userChoice-1]);         


         return;

      }

      else { //사용자모드에서의 유효성 검사

         System.out.println("잘못된 입력입니다");
         check=true; //오류 발생! buyMode를 다시 실행하기위한 장치
         return;
      }
      
   }
   
   //2-1.구매모드(2에서 선택한 음료에 대해 재고 -1 기능, money-price 기능, 돈이 충분한지 확인하는 기능, 재고가 없으면 다시실행하기 기능)
   static void buy(Drink drink) {
      check=false;//check를 false로 초기화

      if(drink.cnt==0) {//재고가 없으면 다시실행하기 기능

         System.out.println("현재 "+drink.name+" 재고가 없습니다!");
         check=true;//오류 발생! buyMode를 다시 실행하기위한 장치

      }

      if(drink.cnt>=1) {//재고가 있다면

         if(money-drink.price>=0) {//( 재고가 있는데 )금액이 충분하다면

            System.out.print(drink.name+" 구매!");

            drink.cnt--; //재고 있으면 하나 샀으니깐 cnt--;

            money-=drink.price;

            System.out.println("[잔액 : "+money+"원]");

            

         }
         else {//( 재고가 있는데 )금액이 부족하다면

            System.out.println("잔액이 부족합니다.");// 잔액이 부족한 경우 돈을 다시 입력해야한다.
            
         }

      }

   }

   //2-2.구매모드(음료 구매 가능 여부 표시)
   static void show(Drink drink){
      if(drink.cnt<=0){ //재고가 없으면

         System.out.println("(X)");   
         return;

      }

      if(money-drink.price>=0) {// ( 재고가 있는데 ) 돈이 충분해 살 수 있으면

         System.out.println("(●)");

      }

      else {// ( 재고가 있는데 ) 돈이 부족해 살 수 없으면

         System.out.println("(○)");

      }

   }
   
   //3.관리자모드(재고 목록 출력 및 어떤 음료의 재고를 추가할 지 선택, 관리자모드 종료하기 기능, 관리자모드에서 유효성검사)
   static void adminMode(Drink[] drink) {
      int adChoice; //관리자모드에서의 입력
      
      System.out.println("★★★관리자모드★★★");
      while(true) {         
         int i=0;// 인덱스 번호 초기화
         //재고확인 목록 출력
         System.out.println("====================");
         while(i<drink.length) {
            for(Drink d:drink) {
               System.out.print((i+1) + ". ");
               i++;
               System.out.print(d);
            }
         }
         System.out.println("====================\n");
         System.out.println("입고하실 음료를 선택해주세요 [0입력:종료]");
         System.out.println("(음료 당 최대 10개까지 적재 가능합니다.)");
         System.out.println();


         adChoice = sc.nextInt();

         if(adChoice==0) { //0입력시 종료
            System.out.println("관리자모드를 종료합니다");
            break;
         }
         else if(adChoice>0&&adChoice<=6) {//제대로 입력되면 정상작동
            admin(drink[adChoice-1]);

         }
         else { //관리자모드에서의 유효성 검사
            System.out.println("잘못된 입력입니다.");
         }
      }
   }
   
   //3-1.관리자모드(3에서 선택한 재고 추가)
   static void admin(Drink drink) {

      System.out.print("추가 할 "+drink.name+" 재고 수:");
      int num = sc.nextInt();

      if(drink.cnt+num<=drink.max){//기존재고에 재고추가했을시 -> max보다 작거나 같으면

         drink.cnt+=num;//입력받은만큼 재고에 +

      } 

      else if(drink.cnt+num>drink.max) { //넘치면 "적재 가능량을 초과, 재입력"

         System.out.println("최대 재고수량보다 많음. 다시 입력 바람");

      }
      return;
   }


   public static void main(String[] args) {

      Drink [] drink = new Drink[6]; 
      drink[0] = new Coffee("  티오피  ", 5, 500);
      drink[1] = new Coffee("  레쓰비  ", 0, 500);
      drink[2] = new Sports(" 게토레이 ", 5, 1000);
      drink[3] = new Sports("파워에이드", 0, 1000);
      drink[4] = new Tea("옥수수 수염차", 5, 1500);
      drink[5] = new Tea(" 하늘보리 ", 5, 1500);



      while(true) {
         
         check=false; 


         //0.mainScreen
         if(mainScreen()) break;
                                       
         
         if(check) {

            adminMode(drink);//관리자모드

            continue;
         }

         //1.돈입력
         inputMoney();

         //2.구매
         do {
            buyMode(drink); //구매모드
         }while(check);//check -> 변수의 재사용
         
      }

   }

}
