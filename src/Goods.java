import java.io.*;

public class Goods {
	String categoryName; //대분류 이름
	String goodsName; //제품명
	int goodsPrice; //가격
	int goodsNo; //물품번호
	int goodsAmount; //재고수량
	
	//데이터 멤버의 get, set함수
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public int getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsNo(int goodsNo) {
		this.goodsNo = goodsNo;
	}
	public int getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsAmount(int goodsAmount) {
		this.goodsAmount = goodsAmount;
	}
	public int getGoodsAmount() {
		return goodsAmount;
	}

	//재고 관리
	public void addStock(int amount) { //재고를 더함
		goodsAmount += amount;
	}
	public void substractStock(int amount) throws Exception{//재고를 뺌
		if(amount > goodsAmount)
			throw new Exception();
		goodsAmount -= amount;
	}
	
	//파일 저장
	public void saveGoods(DataOutputStream dataOutFile) throws Exception{
		try {
			dataOutFile.writeUTF(categoryName);
			dataOutFile.writeUTF(goodsName);
			dataOutFile.writeInt(goodsPrice);
			dataOutFile.writeInt(goodsNo);
			dataOutFile.writeInt(goodsAmount);
		}
		catch(IOException ioe){
			throw new IOException();
		}
	}
	
	public void openGoods(DataInputStream dataInFile) throws Exception{
		try {
			categoryName = (String) dataInFile.readUTF();
			goodsName = (String) dataInFile.readUTF();
			goodsPrice = (int) dataInFile.readInt();
			goodsNo = (int) dataInFile.readInt();
			goodsAmount = (int) dataInFile.readInt();
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
