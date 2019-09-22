import java.io.*;
import java.util.*;
public class Management {

	int sales = 0; //����
	//������ ����� get, set �Լ�
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
	
	//Goods ��ü�� Goods ����Ʈ�� �����ϴ� �Լ�
	public void insertGoods(String categoryName, String goodsName, int goodsPrice, int goodsAmount)
	{
		Goods goods = new Goods(); 
		int max = 100;
		for(Goods gds : goodsList) {
			if(gds.getGoodsNo() > max) {
				max = gds.getGoodsNo();
			}
		}
		int goodsNo = max+1; //���� id �ڵ��Է� ���� //��ǰ ��ȣ �� ���� ū ���� +1 �� ���� �� ��ǰ�� ��ǰ��ȣ�� �ο�
		goods.setGoodsNo(goodsNo);
		goods.setCategoryName(categoryName);
		goods.setGoodsName(goodsName);
		goods.setGoodsPrice(goodsPrice);
		goods.setGoodsAmount(goodsAmount);
		goodsList.add(goods);
	}
	
	//��ǰ�̸��� �˻��ϸ� ��ǰ��ȣ�� �����ϴ� �Լ�
	//��ǰ �� �Է� ���� ��ǰ �̸��� ������ �ͼ��� �߻�
	public int findGoodsIndex(String goodsName) throws Exception {
		for(Goods gds : goodsList) {
			if(gds.getGoodsName().equals(goodsName)) 
				return goodsList.indexOf(gds);
		}
		throw new Exception();
	}

	//��ǰ�� �ε����� �޾Ƽ� ��ǰ�� �����ϴ� �Լ�
	public void deleteGoods(int index) throws Exception {
		try {
			goodsList.remove(index);
		}
		catch(IndexOutOfBoundsException iobe) { //index�� linkedList�� ������ �Ѿ�� �ͼ��� �߻�
			throw new Exception();
		}
	}
	
	//ī�װ��� ���� ���� ��ǰ �˷��ִ� �Լ� 
	//��ǰ �迭 �����ؼ� ����
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

	//���� ���� ���� ���� �����ؾ� �ϴ����� �������ִ� �Լ�
	public int sellEsimate(int index, int sellCount) {
		return goodsList.get(index).getGoodsPrice()*sellCount;
	}
	
	
	//���� Ȯ�� �� ���� ���Ÿ� ���ϴ� �Լ�
	public int sell(int index, int sellCount) throws Exception{
		try {
			goodsList.get(index).substractStock(sellCount); //��� ����
		}
		catch(Exception e) {
			throw new Exception();
		}
		sales += goodsList.get(index).getGoodsPrice()*sellCount; // ���� ����
		return goodsList.get(index).getGoodsPrice()*sellCount;
	}
	
	
	public void saveManagement(DataOutputStream dataOutFile) throws Exception{
		DataOutputStream goodsDataOutFile = null;
		try {
			goodsDataOutFile = new DataOutputStream(new FileOutputStream("GoodsFile.txt"));
			
			dataOutFile.writeInt(sales);
			
			//Goods ��ü �����϶�� �ϱ�
			for(Goods gds : goodsList)
				gds.saveGoods(goodsDataOutFile);
		}
		catch(IOException ioe){
			System.out.println("���Ϸ� ����� �� �����ϴ�.");
		}
	}
	
	public void openManagement(DataInputStream dataInFile) throws Exception{
		try {
			sales = (int) dataInFile.readInt();
			DataInputStream goodsDataInFile = null;
			goodsDataInFile = new DataInputStream(new FileInputStream("GoodsFile.txt"));
			while(true) {
				//Goods ��ü �ҷ�����
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
