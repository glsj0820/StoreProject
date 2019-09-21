import java.io.*;

public class Goods {
	String categoryName; //��з� �̸�
	String goodsName; //��ǰ��
	int goodsPrice; //����
	int goodsNo; //��ǰ��ȣ
	int goodsAmount; //������
	
	//������ ����� get, set�Լ�
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

	//��� ����
	public void addStock(int amount) { //��� ����
		goodsAmount += amount;
	}
	public void substractStock(int amount) throws Exception{//��� ��
		if(amount > goodsAmount)
			throw new Exception();
		goodsAmount -= amount;
	}
	
	//���� ����
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
