import java.io.*;
public class Management {

	int count = 0; //��ǰ ����	
	int sales = 0; //����
	//������ ����� get, set �Լ�
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
	
	//Goods ��ü�� Goods ����Ʈ�� �����ϴ� �Լ�
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
		int goodsNo = max+1; //���� id �ڵ��Է� ���� //��ǰ ��ȣ �� ���� ū ���� +1 �� ���� �� ��ǰ�� ��ǰ��ȣ�� �ο�
		goodsList[count].setGoodsNo(goodsNo);
		goodsList[count].setCategoryName(categoryName);
		goodsList[count].setGoodsName(goodsName);
		goodsList[count].setGoodsPrice(goodsPrice);
		goodsList[count].setGoodsAmount(goodsAmount);
		count++; //��ǰ �Է¹����� ��ǰ�� ���� ����
	}
	
	//��ǰ�̸��� �˻��ϸ� ��ǰ��ȣ�� �����ϴ� �Լ�
	//��ǰ �� �Է� ���� ��ǰ �̸��� ������ �ͼ��� �߻�
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

	//��ǰ�� �ε����� �޾Ƽ� ��ǰ�� �����ϴ� �Լ�
	public void deleteGoods(int index) {
		while(index<count-1) {
			goodsList[index] = goodsList[index+1];
			index++;
		}
		goodsList[index] = null;
		count--; //��ǰ ���� �� ��ǰ�� ���� ����
	}
	
	//ī�װ��� ���� ���� ��ǰ �˷��ִ� �Լ� 
	//��ǰ �迭 �����ؼ� ����
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
	
	//���� ���� ���� ���� �����ؾ� �ϴ����� �������ִ� �Լ�
	public int sellEsimate(int index, int sellCount) {
		return goodsList[index].getGoodsPrice()*sellCount;
	}
	
	//���� Ȯ�� �� ���� ���Ÿ� ���ϴ� �Լ�
	public int sell(int index, int sellCount) throws Exception{
		/*if(goodsList[index].getGoodsAmount() < sellCount) //++��� ����̳� ������ �Ŵ����� �ƴ϶� ���ü�� 
			throw new Exception();*/
		try {
			goodsList[index].substractStock(sellCount); //��� ����
		}
		catch(Exception e) {
			throw new Exception();
		}
		sales += goodsList[index].getGoodsPrice()*sellCount; // ���� ����
		return goodsList[index].getGoodsPrice()*sellCount;
	}
	
	public void saveManagement(DataOutputStream dataOutFile) throws Exception{
		
		DataOutputStream goodsDataOutFile = null;
		try {
			goodsDataOutFile = new DataOutputStream(new FileOutputStream("GoodsFile.txt"));
			
			dataOutFile.writeInt(sales);
			dataOutFile.writeInt(count);
			
			//Goods ��ü �����϶�� �ϱ�
			for(int i=0; i<count; i++)
				goodsList[i].saveGoods(goodsDataOutFile);
		}
		catch(IOException ioe){
			System.out.println("���Ϸ� ����� �� �����ϴ�.");
		}
	}
	
	public void openManagement(DataInputStream dataInFile) throws Exception{
		try {
			while(true) {
				sales = (int) dataInFile.readInt();
				count = (int) dataInFile.readInt();
				
				DataInputStream goodsDataInFile = null;
				goodsDataInFile = new DataInputStream(new FileInputStream("GoodsFile.txt"));
				//Goods ��ü �ҷ�����
				try {
					for(int i=0; i<count; i++) {
						goodsList[i] = new Goods();
						goodsList[i].openGoods(goodsDataInFile);
					}
				}
				catch (java.lang.ArrayIndexOutOfBoundsException e){ //Goods ��ü �迭�� ���� �ʰ��Ͽ� �ͼ��� �߻� �� ��ǰ�� ���� �� �ִ� ��ŭ�� �ְ� �ͼ��� ������
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
