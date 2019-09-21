/*���α׷� �̸�:������ ���α׷�
���α׷� ����:������ ������ó�� ��ǰ�� �����ϰ�, �մ��� �޾� ���Ÿ� ó���ϴ� ���α׷�
�ۼ���: 2019/6/23
�ۼ���: �̼���*/

import java.util.Scanner;
import java.io.*;

public class UserInterface {
	public static void main(String args[]) {

		Scanner scan = new Scanner(System.in);
		String category; //�Է¹��� ��з�
		String name; //�Է¹��� ��ǰ�̸�
		int price = 0; //�Է¹��� ��ǰ ����
		int amount = 0; //�Է¹��� �԰����
		int goodsLimit = 0; //�Է¹��� �ִ� ��ǰ ����
		int menu = 0; //�Է¹��� �޴� ��ȣ
		int submenu1 = 0; //�Է¹��� ����޴� ��ȣ
		int submenu2 = 0; //�Է¹��� ����޴� ��ȣ

		if(goodsLimit == 0) {
			while(true) {
				System.out.println("******* ���� ���� �� *******");
				System.out.println("�ִ� ��ǰ ������ �Է��Ͻÿ�.");
				try {
					goodsLimit = scan.nextInt(); //�ִ� ��ǰ ������ �Է�
				}
				catch(java.util.InputMismatchException e) { //���ڸ� �Է����� �ʾ��� �� �ͼ��� �߻�
					System.out.println("���ڸ� �Է����ּ���.");
					scan.nextLine();
					continue;
				}	
				if(goodsLimit <= 0) //���ڰ� 1�̻��� ���� �ƴ� ��
					System.out.println("1�̻��� ���� �Է����ּ���.");
				else
					break;
			}
		}
		Management manager = new Management(goodsLimit); //manager��ü ���� �� ����
		
		while(true) {
			//�޴�
			Menu.initialMenu();
			try {
				menu = scan.nextInt();
			}
			catch(java.util.InputMismatchException e) { //nextInt()�Լ����� ���ڸ� �Է����� �ʾ��� �� �ͼ��� �߻�
				System.out.println("���ڸ� �Է����ּ���.");
				scan.nextLine();
			}
			if(menu == 1) { //(1) �����
				while(true) {
					Menu.customerMenu();
					try {
						submenu1 = scan.nextInt();
					}
					catch(java.util.InputMismatchException e) { //nextInt()�Լ����� ���ڸ� �Է����� �ʾ��� �� �ͼ��� �߻�
						System.out.println("���ڸ� �Է����ּ���.");
						scan.nextLine();
						continue;
					}
					switch(submenu1) {
					case 1: //(1) ��ǰ ����
						if(manager.getCount() <= 0) //��ǰ�� �ϳ��� ��ϵ��� �ʾ��� �� 
							System.out.println("���� ������ ��ǰ�� �������� �ʽ��ϴ�.");
						else {
							//ī�װ� �˻�
							System.out.println("******* ��ǰ ���� *******");
							System.out.println("------- ī�װ� �˻��ϱ� -------");
							System.out.print("ī�װ� �̸� : ");
							category = scan.next();
							//�Է¹��� ī�װ��� �ش�Ǵ� ��ǰ ���
							System.out.println("\t��ǰ�̸�\t��ǰ����\t����");
							try {
								for(int i=0; manager.findGoods(category)[i] != null; i++) {
									System.out.print(i+1+")\t");
									System.out.print(manager.findGoods(category)[i].getGoodsName()+"\t");
									System.out.print(manager.findGoods(category)[i].getGoodsPrice()+"\t");
									System.out.print(manager.findGoods(category)[i].getGoodsAmount()+"\n");
								}
							}
							catch(Exception e) { //�Է��� ī�װ��� ���� ��ǰ�� ������ �ͼ��� �߻�
								System.out.println("�Է��� ī�װ��� �������� �ʽ��ϴ�.");
								break;
							}
							//������ ��ǰ�� ���� �ޱ�
							System.out.println("--------------------------");
							System.out.print("������ ��ǰ ��ȣ : ");
							int num = 0;
							try {
								num = scan.nextInt();
							}
							catch(java.util.InputMismatchException e) { //nextInt()�Լ����� ���ڸ� �Է����� �ʾ��� �� �ͼ��� �߻�
								System.out.println("���ڸ� �Է����ּ���.");
								scan.nextLine();
								break;
							}
							String customerGoodsName;
							int customerGoodsIndex;
							try {
								customerGoodsName = manager.findGoods(category)[num-1].getGoodsName(); //������ ��ǰ ��ȣ�� �ش��ϴ� �̸��� customerGoodsName�� ����
								customerGoodsIndex = manager.findGoodsIndex(customerGoodsName); //������ ��ǰ�� Goods��ü �迭������ index�� ����
							}
							catch(Exception e) { //�Է��� ��ǰ�� �������� ������ �ͼ��� �߻�
								System.out.println("������ ��ǰ�� �������� �ʽ��ϴ�.");
								break;
							}
							System.out.print(customerGoodsName+"�� ���� ���� : ");
							try {
								amount = scan.nextInt();
							}
							catch(java.util.InputMismatchException e) { //nextInt()�Լ����� ���ڸ� �Է����� �ʾ��� �� �ͼ��� �߻�
								System.out.println("���ڸ� �Է����ּ���.");
								scan.nextLine();
								break;
							}
							//����
							System.out.println("�հ� �ݾ� : "+manager.sellEsimate(customerGoodsIndex, amount)); //����ڰ� ������ �� �հ�ݾ� ���
							System.out.print("�����Ͻðڽ��ϱ�?(y/n)");
							String estimate = scan.next(); //���� Ȯ���� ����
							if(estimate.equals("y") || estimate.equals("Y")) { //���� Ȯ��
								try {
									System.out.println("���� �ݾ� : "+manager.sell(customerGoodsIndex, amount)); //�����ϸ鼭 �� ���� �ݾ� ���
								}
								catch(Exception e) {
									System.out.println("���� �����Ͽ����ϴ�.");
									System.out.println("��� ����. ���� "+customerGoodsName+"�� ���� "+manager.goodsList[customerGoodsIndex].getGoodsAmount()+"�Դϴ�.");
									break;
								}
								System.out.println("������ �Ϸ�Ǿ����ϴ�."); //���� ���� �� ���� ���
							}
							else if(estimate.equals("n") || estimate.equals("N")) //���� ���
								System.out.println("���Ű� ��ҵǾ����ϴ�.");
						}
						break;
					case 2: //(2) ��ǰ ����Ʈ ���
						System.out.println("******* ��ǰ ����Ʈ ��� *******");
						System.out.println("��ǰ��ȣ\t��з�\t��ǰ�̸�\t��ǰ����\t����");
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
					case 3: //(3) ������
						break;
					default: //submenu1�� ������ ��ȣ �ܿ� �ٸ� ��ȣ�� �ԷµǾ��� ��� �ٽ� �Է��϶�� ����  ���
						System.out.println("�ٽ� �Է����ּ���. "); 
						break;
					}
					if(submenu1 == 3)
						break; //�ݺ��� ����
				}
			}
			else if(menu == 2) { //(2) ������
				while(true) {
					Menu.managerMenu();
					try {
						submenu2 = scan.nextInt();
					}
					catch(java.util.InputMismatchException e) { //nextInt()�Լ����� ���ڸ� �Է����� �ʾ��� �� �ͼ��� �߻�
						System.out.println("���ڸ� �Է����ּ���.");
						scan.nextLine();
						continue;
					}
					switch(submenu2) {
					case 1: //(1) ��ǰ �߰�
						try {
							System.out.println("******* ��ǰ �߰� *******");
							System.out.print("��з� : ");
							category = scan.next();
							System.out.print("��ǰ �̸� : ");
							name = scan.next();
							System.out.print("��ǰ ���� : ");
							price = scan.nextInt();
							System.out.print("�԰��� ���� : ");
							amount = scan.nextInt();
							manager.insertGoods(category, name, price, amount); //��ǰ�� ���� �Է��Ͽ� insertGoods�Լ��� ���� ��ǰ �߰�
						}
						catch(java.util.InputMismatchException e) { //nextInt()�Լ����� ���ڸ� �Է����� �ʾ��� �� �ͼ��� �߻�
							System.out.println("���ڸ� �Է����ּ���.");
							scan.nextLine();
							break;
						}
						catch (java.lang.ArrayIndexOutOfBoundsException e){ //Goods ��ü �迭�� ���� �ʰ��Ͽ� �ͼ��� �߻� �� ��ǰ ���� �ʰ� ���� ���
							System.out.println("��ǰ ���� �ʰ�");
						}
						break;
					case 2: //(2) ��ǰ ����
						System.out.println("******* ��ǰ ���� *******");
						if(manager.getCount() <= 0) //��ǰ�� �ϳ��� ��ϵ��� �ʾ��� �� 
							System.out.println("���� ������ ��ǰ�� �������� �ʽ��ϴ�.");
						else {
							System.out.println("------- ī�װ� �˻��ϱ� -------");
							System.out.print("ī�װ� �̸� : ");
							category = scan.next();
							//�Է¹��� ī�װ��� �ش�Ǵ� ��ǰ ���
							try {
								System.out.println("\t��ǰ�̸�\t��ǰ����\t����");
								for(int i=0; manager.findGoods(category)[i] != null; i++) {
									System.out.print(i+1+")\t");
									System.out.print(manager.findGoods(category)[i].getGoodsName()+"\t");
									System.out.print(manager.findGoods(category)[i].getGoodsPrice()+"\t");
									System.out.print(manager.findGoods(category)[i].getGoodsAmount()+"\n");
								}
							}
							catch(Exception e) { //�Է��� ī�װ��� ���� ��ǰ�� ������ �ͼ��� �߻�
								System.out.println("�Է��� ī�װ��� �������� �ʽ��ϴ�.");
								break;
							}
							System.out.println("--------------------------");
							System.out.print("������ ��ǰ ��ȣ : ");
							int num = 0;
							try {
								num = scan.nextInt();
							}
							catch(java.util.InputMismatchException e) { //nextInt()�Լ����� ���ڸ� �Է����� �ʾ��� �� �ͼ��� �߻�
								System.out.println("���ڸ� �Է����ּ���.");
								scan.nextLine();
								break;
							}
							String goodsName;
							try {
								goodsName = manager.findGoods(category)[num-1].getGoodsName(); //������ ��ǰ ��ȣ�� �ش��ϴ� �̸��� customerGoodsName�� ����
								manager.deleteGoods(manager.findGoodsIndex(manager.findGoods(category)[num-1].getGoodsName())); //��ǰ ����
								System.out.println(goodsName+" ��ǰ�� �����Ͽ����ϴ�."); //��ǰ ���� ���� �� ���� ���
							}
							catch(Exception e) { //�Է��� ��ǰ�� �������� ������ �ͼ��� �߻�
								System.out.println("������ ��ǰ�� �������� �ʽ��ϴ�.");
								break;
							}
						}
						break;
					case 3: //(3) ��ǰ����Ʈ ���
						System.out.println("******* ��ǰ ����Ʈ ��� *******");
						System.out.println("��ǰ��ȣ\t��з�\t��ǰ�̸�\t��ǰ����\t����");
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
					case 4: //(4) ���� Ȯ��
						System.out.println("******* ���� *******");
						System.out.println("���� : "+manager.getSales());
						break;
					case 5: //(5) �����ϱ�
						DataOutputStream dataWriteFile = null;
						try {
							dataWriteFile = new DataOutputStream(new FileOutputStream("ManagementFile.txt")); //������ ���� ��ü ����
							manager.saveManagement(dataWriteFile); //����
							System.out.println("������ �Ϸ�Ǿ����ϴ�."); //���� �Ϸ� �� ���� ���
						}
						catch(IOException ioe){
							System.out.println("������ �� �����ϴ�.");
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
							dataReadFile = new DataInputStream(new FileInputStream("ManagementFile.txt")); // �ҷ��� ���� ��ü ����
							manager.openManagement(dataReadFile); //�ҷ�����
							
						}
						catch (java.lang.ArrayIndexOutOfBoundsException e){ //Goods ��ü �迭�� ���� �ʰ��Ͽ� �ͼ��� �߻� �� ��ǰ ���� �ʰ� ���� ���
							System.out.println("��ǰ ���� �ʰ�. ���� ������ ��ǰ�� �ҷ��ɴϴ�.");
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
						System.out.println("�ҷ����� ����"); //�ҷ����� �Ϸ� �� ���� ���
						break;
					case 7: //(7) ������
						break;
					default: //submenu2�� ������ ��ȣ �ܿ� �ٸ� ��ȣ�� �ԷµǾ��� ��� �ٽ� �Է��϶�� ����  ���
						System.out.println("�ٽ� �Է����ּ���. "); 
						break;
					}
					if(submenu2 == 7)
						break; //�ݺ��� ����
				}
			}
			else if(menu == 3) System.exit(0); //(3) ���α׷� ����
			else {//menu�� ������ ��ȣ �ܿ� �ٸ� ��ȣ�� �ԷµǾ��� ��� �ٽ� �Է��϶�� ����  ���
				System.out.println("�ٽ� �Է����ּ���. "); 
			}
		}

	}
}

