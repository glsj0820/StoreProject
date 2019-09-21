/*프로그램 이름:편의점 프로그램
프로그램 설명:편의점 포스기처럼 상품을 관리하고, 손님을 받아 구매를 처리하는 프로그램
작성일: 2019/6/23
작성자: 이선주*/

import java.util.Scanner;
import java.io.*;

public class UserInterface {
	public static void main(String args[]) {

		Scanner scan = new Scanner(System.in);
		String category; //입력받을 대분류
		String name; //입력받을 상품이름
		int price = 0; //입력받을 상품 가격
		int amount = 0; //입력받을 입고수량
		int goodsLimit = 0; //입력받을 최대 물품 수량
		int menu = 0; //입력받을 메뉴 번호
		int submenu1 = 0; //입력받을 서브메뉴 번호
		int submenu2 = 0; //입력받을 서브메뉴 번호

		if(goodsLimit == 0) {
			while(true) {
				System.out.println("******* 영업 시작 전 *******");
				System.out.println("최대 물품 수량을 입력하시오.");
				try {
					goodsLimit = scan.nextInt(); //최대 물품 수량을 입력
				}
				catch(java.util.InputMismatchException e) { //숫자를 입력하지 않았을 때 익셉션 발생
					System.out.println("숫자를 입력해주세요.");
					scan.nextLine();
					continue;
				}	
				if(goodsLimit <= 0) //숫자가 1이상의 수가 아닐 때
					System.out.println("1이상의 수를 입력해주세요.");
				else
					break;
			}
		}
		Management manager = new Management(goodsLimit); //manager객체 선언 및 생성
		
		while(true) {
			//메뉴
			Menu.initialMenu();
			try {
				menu = scan.nextInt();
			}
			catch(java.util.InputMismatchException e) { //nextInt()함수에서 숫자를 입력하지 않았을 때 익셉션 발생
				System.out.println("숫자를 입력해주세요.");
				scan.nextLine();
			}
			if(menu == 1) { //(1) 사용자
				while(true) {
					Menu.customerMenu();
					try {
						submenu1 = scan.nextInt();
					}
					catch(java.util.InputMismatchException e) { //nextInt()함수에서 숫자를 입력하지 않았을 때 익셉션 발생
						System.out.println("숫자를 입력해주세요.");
						scan.nextLine();
						continue;
					}
					switch(submenu1) {
					case 1: //(1) 상품 구매
						if(manager.getCount() <= 0) //상품이 하나도 등록되지 않았을 때 
							System.out.println("현재 구매할 상품이 존재하지 않습니다.");
						else {
							//카테고리 검색
							System.out.println("******* 상품 구매 *******");
							System.out.println("------- 카테고리 검색하기 -------");
							System.out.print("카테고리 이름 : ");
							category = scan.next();
							//입력받은 카테고리에 해당되는 물품 출력
							System.out.println("\t상품이름\t상품가격\t수량");
							try {
								for(int i=0; manager.findGoods(category)[i] != null; i++) {
									System.out.print(i+1+")\t");
									System.out.print(manager.findGoods(category)[i].getGoodsName()+"\t");
									System.out.print(manager.findGoods(category)[i].getGoodsPrice()+"\t");
									System.out.print(manager.findGoods(category)[i].getGoodsAmount()+"\n");
								}
							}
							catch(Exception e) { //입력한 카테고리에 속한 물품이 없으면 익셉션 발생
								System.out.println("입력한 카테고리가 존재하지 않습니다.");
								break;
							}
							//구매할 물품과 수량 받기
							System.out.println("--------------------------");
							System.out.print("구매할 상품 번호 : ");
							int num = 0;
							try {
								num = scan.nextInt();
							}
							catch(java.util.InputMismatchException e) { //nextInt()함수에서 숫자를 입력하지 않았을 때 익셉션 발생
								System.out.println("숫자를 입력해주세요.");
								scan.nextLine();
								break;
							}
							String customerGoodsName;
							int customerGoodsIndex;
							try {
								customerGoodsName = manager.findGoods(category)[num-1].getGoodsName(); //구매할 상품 번호에 해당하는 이름을 customerGoodsName에 저장
								customerGoodsIndex = manager.findGoodsIndex(customerGoodsName); //구매할 물품이 Goods객체 배열에서의 index를 저장
							}
							catch(Exception e) { //입력한 상품이 존재하지 않으면 익셉션 발생
								System.out.println("구매할 상품이 존재하지 않습니다.");
								break;
							}
							System.out.print(customerGoodsName+"의 구매 수량 : ");
							try {
								amount = scan.nextInt();
							}
							catch(java.util.InputMismatchException e) { //nextInt()함수에서 숫자를 입력하지 않았을 때 익셉션 발생
								System.out.println("숫자를 입력해주세요.");
								scan.nextLine();
								break;
							}
							//구매
							System.out.println("합계 금액 : "+manager.sellEsimate(customerGoodsIndex, amount)); //사용자가 지불할 총 합계금액 출력
							System.out.print("구매하시겠습니까?(y/n)");
							String estimate = scan.next(); //구매 확정을 결정
							if(estimate.equals("y") || estimate.equals("Y")) { //구매 확정
								try {
									System.out.println("결제 금액 : "+manager.sell(customerGoodsIndex, amount)); //결제하면서 총 결제 금액 출력
								}
								catch(Exception e) {
									System.out.println("결제 실패하였습니다.");
									System.out.println("재고 부족. 현재 "+customerGoodsName+"의 재고는 "+manager.goodsList[customerGoodsIndex].getGoodsAmount()+"입니다.");
									break;
								}
								System.out.println("결제가 완료되었습니다."); //결제 성공 시 문구 출력
							}
							else if(estimate.equals("n") || estimate.equals("N")) //구매 취소
								System.out.println("구매가 취소되었습니다.");
						}
						break;
					case 2: //(2) 상품 리스트 출력
						System.out.println("******* 상품 리스트 출력 *******");
						System.out.println("상품번호\t대분류\t상품이름\t상품가격\t수량");
						for(int i=0; i<manager.getCount(); i++) {
							System.out.print(manager.goodsList[i].getGoodsNo()+"\t");
							System.out.print(manager.goodsList[i].getCategoryName()+'\t');
							System.out.print(manager.goodsList[i].getGoodsName()+'\t');
							System.out.print(manager.goodsList[i].getGoodsPrice());
							System.out.print('\t');
							System.out.print(manager.goodsList[i].getGoodsAmount());
							System.out.print("\n");
						}
						break;
					case 3: //(3) 나가기
						break;
					default: //submenu1이 정해진 번호 외에 다른 번호가 입력되었을 경우 다시 입력하라는 문구  출력
						System.out.println("다시 입력해주세요. "); 
						break;
					}
					if(submenu1 == 3)
						break; //반복문 종료
				}
			}
			else if(menu == 2) { //(2) 관리자
				while(true) {
					Menu.managerMenu();
					try {
						submenu2 = scan.nextInt();
					}
					catch(java.util.InputMismatchException e) { //nextInt()함수에서 숫자를 입력하지 않았을 때 익셉션 발생
						System.out.println("숫자를 입력해주세요.");
						scan.nextLine();
						continue;
					}
					switch(submenu2) {
					case 1: //(1) 상품 추가
						try {
							System.out.println("******* 상품 추가 *******");
							System.out.print("대분류 : ");
							category = scan.next();
							System.out.print("상품 이름 : ");
							name = scan.next();
							System.out.print("상품 가격 : ");
							price = scan.nextInt();
							System.out.print("입고할 수량 : ");
							amount = scan.nextInt();
							manager.insertGoods(category, name, price, amount); //상품의 정보 입력하여 insertGoods함수를 통해 상품 추가
						}
						catch(java.util.InputMismatchException e) { //nextInt()함수에서 숫자를 입력하지 않았을 때 익셉션 발생
							System.out.println("숫자를 입력해주세요.");
							scan.nextLine();
							break;
						}
						catch (java.lang.ArrayIndexOutOfBoundsException e){ //Goods 객체 배열의 범위 초과하여 익셉션 발생 시 물품 수량 초과 문구 출력
							System.out.println("물품 수량 초과");
						}
						break;
					case 2: //(2) 상품 삭제
						System.out.println("******* 상품 삭제 *******");
						if(manager.getCount() <= 0) //상품이 하나도 등록되지 않았을 때 
							System.out.println("현재 삭제할 상품이 존재하지 않습니다.");
						else {
							System.out.println("------- 카테고리 검색하기 -------");
							System.out.print("카테고리 이름 : ");
							category = scan.next();
							//입력받은 카테고리에 해당되는 물품 출력
							try {
								System.out.println("\t상품이름\t상품가격\t수량");
								for(int i=0; manager.findGoods(category)[i] != null; i++) {
									System.out.print(i+1+")\t");
									System.out.print(manager.findGoods(category)[i].getGoodsName()+"\t");
									System.out.print(manager.findGoods(category)[i].getGoodsPrice()+"\t");
									System.out.print(manager.findGoods(category)[i].getGoodsAmount()+"\n");
								}
							}
							catch(Exception e) { //입력한 카테고리에 속한 물품이 없으면 익셉션 발생
								System.out.println("입력한 카테고리가 존재하지 않습니다.");
								break;
							}
							System.out.println("--------------------------");
							System.out.print("삭제할 상품 번호 : ");
							int num = 0;
							try {
								num = scan.nextInt();
							}
							catch(java.util.InputMismatchException e) { //nextInt()함수에서 숫자를 입력하지 않았을 때 익셉션 발생
								System.out.println("숫자를 입력해주세요.");
								scan.nextLine();
								break;
							}
							String goodsName;
							try {
								goodsName = manager.findGoods(category)[num-1].getGoodsName(); //삭제할 상품 번호에 해당하는 이름을 customerGoodsName에 저장
								manager.deleteGoods(manager.findGoodsIndex(manager.findGoods(category)[num-1].getGoodsName())); //상품 삭제
								System.out.println(goodsName+" 상품을 삭제하였습니다."); //상품 삭제 성공 시 문구 출력
							}
							catch(Exception e) { //입력한 상품이 존재하지 않으면 익셉션 발생
								System.out.println("삭제할 상품이 존재하지 않습니다.");
								break;
							}
						}
						break;
					case 3: //(3) 상품리스트 출력
						System.out.println("******* 상품 리스트 출력 *******");
						System.out.println("상품번호\t대분류\t상품이름\t상품가격\t수량");
						for(int i=0; i<manager.getCount(); i++) {
							System.out.print(manager.goodsList[i].getGoodsNo()+"\t");
							System.out.print(manager.goodsList[i].getCategoryName()+'\t');
							System.out.print(manager.goodsList[i].getGoodsName()+'\t');
							System.out.print(manager.goodsList[i].getGoodsPrice());
							System.out.print('\t');
							System.out.print(manager.goodsList[i].getGoodsAmount());
							System.out.print("\n");
						}
						break;
					case 4: //(4) 매출 확인
						System.out.println("******* 매출 *******");
						System.out.println("매출 : "+manager.getSales());
						break;
					case 5: //(5) 저장하기
						DataOutputStream dataWriteFile = null;
						try {
							dataWriteFile = new DataOutputStream(new FileOutputStream("ManagementFile.txt")); //저장할 파일 객체 오픈
							manager.saveManagement(dataWriteFile); //저장
							System.out.println("저장이 완료되었습니다."); //저장 완료 시 문구 출력
						}
						catch(IOException ioe){
							System.out.println("저장할 수 없습니다.");
						}
						catch(Exception e) {
						}
						finally{
							try{
								dataWriteFile.close();
							}
							catch(Exception e){
								
							}
						}
						break;
					case 6:
						DataInputStream dataReadFile = null;
						try {
							dataReadFile = new DataInputStream(new FileInputStream("ManagementFile.txt")); // 불러올 파일 객체 오픈
							manager.openManagement(dataReadFile); //불러오기
							
						}
						catch (java.lang.ArrayIndexOutOfBoundsException e){ //Goods 객체 배열의 범위 초과하여 익셉션 발생 시 물품 수량 초과 문구 출력
							System.out.println("물품 수량 초과. 수용 가능한 물품만 불러옵니다.");
							break;
						}
						catch(FileNotFoundException fnfe) {
						}
						catch(EOFException eofe){
						}
						catch(Exception e) {
						}
						finally{
							try{
								dataReadFile.close();
							}
							catch(Exception e){
							}
						}
						System.out.println("불러오기 성공"); //불러오기 완료 시 문구 출력
						break;
					case 7: //(7) 나가기
						break;
					default: //submenu2가 정해진 번호 외에 다른 번호가 입력되었을 경우 다시 입력하라는 문구  출력
						System.out.println("다시 입력해주세요. "); 
						break;
					}
					if(submenu2 == 7)
						break; //반복문 종료
				}
			}
			else if(menu == 3) System.exit(0); //(3) 프로그램 종료
			else {//menu가 정해진 번호 외에 다른 번호가 입력되었을 경우 다시 입력하라는 문구  출력
				System.out.println("다시 입력해주세요. "); 
			}
		}

	}
}

