
public class Menu { //메뉴
	public static void initialMenu() {
		System.out.println("############ 편의점 프로그램 ############");
		System.out.println("(1) 사용자");
		System.out.println("(2) 관리자");
		System.out.println("(3) 프로그램 종료");
	}
	
	public static void customerMenu() {
		System.out.println("############ 사용자 ############");
		System.out.println("(1) 상품 구매");
		System.out.println("(2) 상품 리스트 출력");
		System.out.println("(3) 나가기");	
	}
	
	public static void managerMenu() {
		System.out.println("############ 관리자 ############");
		System.out.println("(1) 상품 추가");
		System.out.println("(2) 상품 삭제");
		System.out.println("(3) 상품리스트 출력");
		System.out.println("(4) 매출 확인");
		System.out.println("(5) 저장하기");
		System.out.println("(6) 불러오기");
		System.out.println("(7) 나가기");
	}
}
