import java.io.*;
public class Management {

	int count = 0; //제품 개수	
	int sales = 0; //매출
	//데이터 멤버의 get, set 함수
	public void setCount(int count) {
		this.count = count;
	}
	public int getCount() {
		return count;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public int getSales() {
		return sales;
	}
	
	Goods goodsList[];
	
	Management(int goodsLimit){
		goodsList = new Goods[goodsLimit];
	}
	
	//Goods 객체를 Goods 리스트에 삽입하는 함수
	public void insertGoods(String categoryName, String goodsName, int goodsPrice, int goodsAmount)
	{
		goodsList[count] = new Goods();
		int max = 100;
		int i=0;
		while(i < count) {
			if(goodsList[i].getGoodsNo()>max) {
				max = goodsList[i].getGoodsNo();
			}
			i++;
		}
		int goodsNo = max+1; //고유 id 자동입력 생성 //상품 번호 중 가장 큰 값에 +1 한 값을 새 상품의 상품번호에 부여
		goodsList[count].setGoodsNo(goodsNo);
		goodsList[count].setCategoryName(categoryName);
		goodsList[count].setGoodsName(goodsName);
		goodsList[count].setGoodsPrice(goodsPrice);
		goodsList[count].setGoodsAmount(goodsAmount);
		count++; //상품 입력받으면 상품의 개수 증가
	}
	
	//상품이름을 검색하면 상품번호를 리턴하는 함수
	//상품 중 입력 받은 상품 이름이 없으면 익셉션 발생
	public int findGoodsIndex(String goodsName) throws Exception {
		int i=0;
		while(i<count) {
			if(goodsList[i].getGoodsName().equals(goodsName)) {
				return i;
			}
			i++;
		}
		throw new Exception();
	}

	//상품의 인덱스를 받아서 상품을 삭제하는 함수
	public void deleteGoods(int index) {
		while(index<count-1) {
			goodsList[index] = goodsList[index+1];
			index++;
		}
		goodsList[index] = null;
		count--; //상품 삭제 시 상품의 개수 감소
	}
	
	//카테고리에 속한 삭제 물품 알려주는 함수 
	//물품 배열 저장해서 리턴
	public Goods[] findGoods (String categoryName) throws Exception {
		int i=0;
		int j=0;
		Goods categoryGoodsArray[] = new Goods[100];
		while(i<count) {
			if(goodsList[i].getCategoryName().equals(categoryName)) {
				categoryGoodsArray[j] = goodsList[i];
				j++;
			}
			i++;
		}
		if(categoryGoodsArray[0] == null)
			throw new Exception();
		return categoryGoodsArray;
	}
	
	//구매 이전 얼마의 돈을 지불해야 하는지를 리턴해주는 함수
	public int sellEsimate(int index, int sellCount) {
		return goodsList[index].getGoodsPrice()*sellCount;
	}
	
	//구매 확정 후 실제 구매를 행하는 함수
	public int sell(int index, int sellCount) throws Exception{
		/*if(goodsList[index].getGoodsAmount() < sellCount) //++재고 계산이나 관리는 매니저가 아니라 굿즈객체가 
			throw new Exception();*/
		try {
			goodsList[index].substractStock(sellCount); //재고 감소
		}
		catch(Exception e) {
			throw new Exception();
		}
		sales += goodsList[index].getGoodsPrice()*sellCount; // 매출 증가
		return goodsList[index].getGoodsPrice()*sellCount;
	}
	
	public void saveManagement(DataOutputStream dataOutFile) throws Exception{
		
		DataOutputStream goodsDataOutFile = null;
		try {
			goodsDataOutFile = new DataOutputStream(new FileOutputStream("GoodsFile.txt"));
			
			dataOutFile.writeInt(sales);
			dataOutFile.writeInt(count);
			
			//Goods 객체 저장하라고 하기
			for(int i=0; i<count; i++)
				goodsList[i].saveGoods(goodsDataOutFile);
		}
		catch(IOException ioe){
			System.out.println("파일로 출력할 수 없습니다.");
		}
	}
	
	public void openManagement(DataInputStream dataInFile) throws Exception{
		try {
			while(true) {
				sales = (int) dataInFile.readInt();
				count = (int) dataInFile.readInt();
				
				DataInputStream goodsDataInFile = null;
				goodsDataInFile = new DataInputStream(new FileInputStream("GoodsFile.txt"));
				//Goods 객체 불러오기
				try {
					for(int i=0; i<count; i++) {
						goodsList[i] = new Goods();
						goodsList[i].openGoods(goodsDataInFile);
					}
				}
				catch (java.lang.ArrayIndexOutOfBoundsException e){ //Goods 객체 배열의 범위 초과하여 익셉션 발생 시 물품을 넣을 수 있는 만큼만 넣고 익셉션 던지기
					count = goodsList.length;
					throw new ArrayIndexOutOfBoundsException();
				}
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
