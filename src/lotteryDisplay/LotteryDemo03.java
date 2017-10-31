package lotteryDisplay;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;


/**
 * 1.��ʾ����ѡ�Ľ��
 *2.���������������ʾ���������
 *3.������ʾ��ʽ
 *4.���ö�������
 *5.�����������룬����������룬ʣ���Ϊ��ѡ
 * @author zzy
 *
 */

public class LotteryDemo03 {
	
	static int[] lotteryBall = new int[7];
	static int redPools =33;
	
	
	public static void main(String[] args) {
		LotteryDemo03 lottery = new LotteryDemo03();
		lottery.lotteryGroups();
		
	}

	//ȷ���û���Ҫ�ֶ����뻹��Ҫȫ��ѡ 
	public int manualOrAuto(Scanner scanner){
		// Scanner scanner = new Scanner(System.in);
		System.out.println("��ѡ���ֶ����뻹�ǻ�ѡ(1:�ֶ�����, 2:ȫ����ѡ)��");
		int moa = scanner.nextInt();
		while(1> moa || 2< moa){
			System.out.println("������������������!");
			scanner.hasNextInt();
			moa = scanner.nextInt();
		}
		return moa;
	}
	
	
	public void lotteryGroups(){
		Scanner scanner = new Scanner(System.in);		//�ڽ����ֶ���������ڻ�ѡ��ر�scanner
		
		int moa = manualOrAuto(scanner);
		
		//�����ֶ�����������
		if (1 == moa) {
			System.out.println("��ѡ�����ֶ�����ģʽ:");
			System.out.println("����:����0Ϊ�Զ���ѡ");

			//0�ĸ���
			int countZero =0;
			//���ٱ�
			int lotTimes = 0;
			for (int i = 0; i < lotteryBall.length; i++) {
				// ����������
				if (i < lotteryBall.length - 1) {
					System.out.println("�������" + (i + 1) + "���������(1~33):");
					//��ȡ����̨�����ֵ
					int next = scanner.nextInt();
					// �жϺ����Ƿ񳬳���Χ
					while (next < 0 || next > 33) {
						System.out.println("����������������ȷ�ĺ��룺");
						// scanner.hasNextInt();
						next = scanner.nextInt();
					}
					lotteryBall[i] = next;
					if(0 == next){
						countZero++;
					}
				}
				// �����������
				else if (i == lotteryBall.length - 1) {
					System.out.println("�������������(1~16):");
					int next = scanner.nextInt();
					// �ж������Ƿ񳬳���Χ
					while (next < 0 || next > 16) {
						System.out.println("����������������ȷ�ĺ��룺");
						// scanner.hasNextInt();
						next = scanner.nextInt();
					}
					lotteryBall[i] = next;
					if(0 == next){
						countZero++;
					}
				} else {
					System.out.println("�ֶ������쳣����!");
				}
				
			}
			
			
			
			//�ж��Ƿ����0�ĺ���,������0ʱ���ж��Ƿ���ж�ע��ע
			//manualAndAutoRed(lotteryBall,scanner,countZero);
			if(0 == countZero){
				manualRed(lotteryBall);
				//ѯ���Ƿ���ӱ���
				lotTimes = lotTimes(scanner);
				printNum(lotteryBall, lotTimes);
			}else {
				//�õ���ѡ����ע
				int groupNum = dupliManualAndAuto(scanner);
				
				if(1 == groupNum){				//1���ֶ�+��ѡ �����
					//ѯ���Ƿ���ӱ���
					lotTimes = lotTimes(scanner);
					onceManualAndAutoRed(lotteryBall,countZero);
					printNum(lotteryBall, lotTimes);
					
				}else{					//�����ֶ�+��ѡ �����
					//ѯ���Ƿ���ӱ���
					lotTimes = lotTimes(scanner);
					int[][] multiGroups = multiManualAndAutoRed(lotteryBall, groupNum,countZero);
					
					System.out.println("multiGroups�ĳ���Ϊ:"+multiGroups.length);
					for(int i =0; i< multiGroups.length;i++){
						printNum(multiGroups[i], lotTimes);					//<----------------------��ӡ�����������Ϊ��������
						System.out.println("������������������������������������Ϊ�ⲿ���ô�ӡֵ����������ֻʣ���һ������");
					}
				}
			}
			scanner.close();
		}
		
		//�����ѡ
		//ȷ��Ҫ���л�ѡ��Ŀ
		if( 2 == moa ){
			System.out.println("��ѡ����ȫ����ѡģʽ:");
			System.out.println("��������л�ѡ����Ŀ(1-99):");
			// Scanner scanner = new Scanner(System.in);
			int groupNum = scanner.nextInt();
			if (groupNum<=0 || groupNum >99) {
				System.out.println("������������������!");
				lotteryGroups();
			}
			else {
				int lotTimes = lotTimes(scanner);
				for(int i=0; i< groupNum;i++){
					int[][] rlGroup = new int[groupNum][lotteryBall.length]; 
					LotteryDemo03 rl = new LotteryDemo03();
					rlGroup[i] = rl.randRedAuto();
					printNum(rlGroup[i],lotTimes);
					if(0 == (i+1)% 5){
						System.out.println("\n==================================================");
					}else {
						
					}
					scanner.close();
				}
			}
		}

	}
	
	//�ֶ���û�л�ѡ�����
	public int[] manualRed(int[] lotteryBall){
			int[] tempGroup = lotteryBall;
		
			//û������0����ֻ����һ�δ�ӡ(��ע������ûʵ��)
			setSequence(tempGroup);
			return tempGroup;
	}
	
	//�ֶ�����ʣ��������1�λ�ѡ
	public int[] onceManualAndAutoRed(int[] tempGroup, int countZero){
		int [] tempball = new int[7];
		//����ɹ�
		setSequenceZeroLast(tempGroup);
		//��0�ĺ�������Զ���ѡ
		//����������Զ�ѡ��
		if(0 == tempGroup[tempGroup.length-1] && countZero == 1 ){
			tempGroup[tempGroup.length-1]=randBlue();
		}else{
			//ʣ��ĺ������ѡ��
			System.out.println("����ʣ����Զ�ѡ��");
			if(0 == tempGroup[tempGroup.length-1]){
				tempGroup[tempGroup.length-1]=randBlue();
			}
			//�Ժ�������Զ�ѡ��
			Random random = new Random();
			for(int i=tempGroup.length-countZero-1; i < tempGroup.length-1; i++){
				//����λ���
				tempGroup[i] = (int) random.nextInt(redPools) + 1;
				// ���Զ�����ʱ����Ƿ�����ظ��ĺ���
				for (int j = 0; j < i; j++) {
					if (tempGroup[j] == tempGroup[i]) {
						j = 0;
						tempGroup[i] = (int) (random.nextInt(redPools) + 1);
					}
				}
			}
		}
		setSequence(tempGroup);
		for(int i=0;i<tempGroup.length;i++){
			tempball[i]=tempGroup[i];
		}
		return tempball;
	}

	
	//�ֶ������ʣ������ѡ
	public int[][] multiManualAndAutoRed(int[] lotteryBall,int groupNum,int countZero){
			
		int[][] multiGroups = new int[groupNum][lotteryBall.length]; 
		for(int i=0; i<groupNum;i++){
			multiGroups[i] = onceManualAndAutoRed(lotteryBall,countZero);
			System.out.println("������������������������������������Ϊ�ڲ���ӡֵ");
			printNum(multiGroups[i], 1);
			System.out.println("������������������������������������Ϊ�ڲ���ӡֵ");
		}
		System.out.println("�Ѿ����multiManualAndAutoRed");

		return multiGroups;
	}
	
	//�Զ���ȫ��ѡ 
	public int[] randRedAuto() {
		Random random = new Random();
		for (int i = 0; i < (lotteryBall.length - 1); i++) {
			lotteryBall[i] = (int) random.nextInt(redPools) + 1;
			// ���Զ�����ʱ����Ƿ�����ظ��ĺ���
			for (int j = 0; j < i; j++) {
				if (lotteryBall[j] == lotteryBall[i]) {
					j = 0;
					lotteryBall[i] = (int) (random.nextInt(redPools) + 1);
				}
			}
		}
		
		//�Ժ�����д�С����
		setSequence(lotteryBall);
		
		//��������ӽ�������
		lotteryBall[lotteryBall.length-1] = randBlue();
		
		//printNum(lotteryBall);
		return lotteryBall;
	}

	
	//ѯ���Ƿ���ӵ�ע�౶
	public int lotTimes(Scanner scanner){
		System.out.println("������Ͷ�뵥ע����(1-99):");
		int lotTimes = scanner.nextInt();
		while(lotTimes <1 ||lotTimes >99){
			System.out.println("��������������1-99�ı���");
			lotTimes = scanner.nextInt();
		}
		return lotTimes;
	}
	
	// ��0���ַŵ�����棬�Ӵ�С��
	public void setSequenceZeroLast(int[] lotteryBall){
		for(int i = 0; i< lotteryBall.length-1; i++){
			//�����0,����ŵ�����棬�������0��ʲô������
			if(0 == lotteryBall[i]){
				for(int j= lotteryBall.length-2; j > (0+i);j--){ //�������򲻶������仯λ��
					if(0 != lotteryBall[j] && i!= j){
						int temp = lotteryBall[j];
						lotteryBall[j] = lotteryBall[i];
						lotteryBall[i] = temp;
					}
				}
			}
		}
		//
		for(int i = 0; i< lotteryBall.length-1; i++){
			for(int j = 0; j < lotteryBall.length - 2; j++) {
				int temp;
				if (lotteryBall[j] > lotteryBall[j + 1] && 0!= lotteryBall[j+1]) {
					temp = lotteryBall[j + 1];
					lotteryBall[j + 1] = lotteryBall[j];
					lotteryBall[j] = temp;
				}
			}
		}
	}
	
	//�Ժ�����д�С����
	public void setSequence(int[] lotteryBall){
		
		// �����ֽ������У���С����
		for (int i = 0; i < (lotteryBall.length - 1); i++) {
			for (int j = 0; j < lotteryBall.length - 2; j++) {
				int temp;
				if (lotteryBall[j] > lotteryBall[j + 1]) {
					temp = lotteryBall[j + 1];
					lotteryBall[j + 1] = lotteryBall[j];
					lotteryBall[j] = temp;
				}
			}
		}
	}

	
	//��������ȷ��
	public int dupliManualAndAuto(Scanner scanner){
		System.out.println("��������0������л�ѡ ���������ѡ����(1-99)��");
		//��ȡ����
		int groupNum = scanner.nextInt();
		while(groupNum<1 || groupNum >99){
			System.out.println("����������������ȷ����:");
			//�õ�����
			groupNum=scanner.nextInt();
		}
		return groupNum;
	}
	
	//��������ĳ�ѡ,��1��16�г�ѡһ������
	private int randBlue(){
		Random random = new Random();
		int blueBall = (int)random.nextInt(16)+1;
		return blueBall;
	}
	
	//��ӡ����
	private static void printNum(int[] balls, int lotTimes){
		for(int temp: balls){
			DecimalFormat df = new DecimalFormat("00");
			System.out.print(df.format(temp)+ "\t");
		}
		System.out.println("����:" + lotTimes);
	}
	
}