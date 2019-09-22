import java.io.*;
import java.util.*;
public class Management {

	int sales = 0; //매출
	//데이터 멤버의 get, set 함수
	public void setSales(int sales) {
		this.sales = sales;
	}
	public int getSales() {
		return sales;
	}
	
	LinkedList<Goods> goodsList;
	
	Management(){
		goodsList = new LinkedList<Goods>();
	}
	
	//Goods 객체를 Goods 리스트에 삽입하는 함수
	public void insertGoods(String categoryName, String goodsName, int goodsPrice, int goodsAmount)
	{
		Goods goods = new Goods(); 
		int max = 100;
		for(Goods gds : goodsList) {
			if(gds.getGoodsNo() > max) {
				max = gds.getGoodsNo();
			}
		}
		int goodsNo = max+1; //고유 id 자동입력 생성 //상품 번호 중 가장 큰 값에 +1 한 값을 새 상품의 상품번호에 부여
		goods.setGoodsNo(goodsNo);
		goods.setCategoryName(categoryName);
		goods.setGoodsName(goodsName);
		goods.setGoodsPrice(goodsPrice);
		goods.setGoodsAmount(goodsAmount);
		goodsList.add(goods);
	}
	
	//상품이름을 검색하면 상품번호를 리턴하는 함수
	//상품 중 입력 받은 상품 이름이 없으면 익셉션 발생
	public int findGoodsIndex(String goodsName) throws Exception {
		for(Goods gds : goodsList) {
			if(gds.getGoodsName().equals(goodsName)) 
				return goodsList.indexOf(gds);
		}
		throw new Exception();
	}

	//상품의 인덱스를 받아서 상품을 삭제하는 함수
	public void deleteGoods(int index) throws Exception {
		try {
			goodsList.remove(index);
		}
		catch(IndexOutOfBoundsException iobe) { //index가 linkedList의 범위를 넘어서면 익셉션 발생
			throw new Exception();
		}
	}
	
	//카테고리에 속한 삭제 물품 알려주는 함수 
	//물품 배열 저장해서 리턴
	public LinkedList<Goods> findGoods (String categoryName) throws Exception {
		LinkedList<Goods> categoryGoodsList = new LinkedList<Goods>();
		for(Goods gds : goodsList) {
			if(gds.getCategoryName().equals(categoryName)) {
				categoryGoodsList.add(gds);
			}
		}
		if(categoryGoodsList.size() <= 0)
			throw new Exception();
		return categoryGoodsList;			
	}

	//구매 이전 얼마의 돈을 지불해야 하는지를 리턴해주는 함수
	public int sellEsimate(int index, int sellCount) {
		return goodsList.get(index).getGoodsPrice()*sellCount;
	}
	
	
	//구매 확정 후 실제 구매를 행하는 함수
	public int sell(int index, int sellCount) throws Exception{
		try {
			goodsList.get(index).substractStock(sellCount); //재고 감소
		}
		catch(Exception e) {
			throw new Exception();
		}
		sales += goodsList.get(index).getGoodsPrice()*sellCount; // 매출 증가
		return goodsList.get(index).getGoodsPrice()*sellCount;
	}
	
	
	public void saveManagement(DataOutputStream dataOutFile) throws Exception{
		DataOutputStream goodsDataOutFile = null;
		try {
			goodsDataOutFile = new DataOutputStream(new FileOutputStream("GoodsFile.txt"));
			
			dataOutFile.writeInt(sales);
			
			//Goods 객체 저장하라고 하기
			for(Goods gds : goodsList)
				gds.saveGoods(goodsDataOutFile);
		}
		catch(IOException ioe){
			System.out.println("파일로 출력할 수 없습니다.");
		}
	}
	
	public void openManagement(DataInputStream dataInFile) throws Exception{
		try {
			sales = (int) dataInFile.readInt();
			DataInputStream goodsDataInFile = null;
			goodsDataInFile = new DataInputStream(new FileInputStream("GoodsFile.txt"));
			while(true) {
				//Goods 객체 불러오기
				Goods gds = new Goods();
				gds.openGoods(goodsDataInFile);
				goodsList.add(gds);
			}
		}
		catch(FileNotFoundException fnfe) {
			throw new FileNotFoundException();
		}
		catch(EOFException eofe){
			throw new EOFException();
		}
		catch(IOException ioe){
			throw new IOException();
		}
	}
}
