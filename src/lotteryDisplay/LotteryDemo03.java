package lotteryDisplay;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;


/**
 * 1.显示出抽选的结果
 *2.将结果进行排序，显示红球和蓝球
 *3.更改显示格式
 *4.设置多重数组
 *5.添加手输入号码，在手输入号码，剩余的为机选
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

	//确认用户是要手动输入还是要全机选 
	public int manualOrAuto(Scanner scanner){
		// Scanner scanner = new Scanner(System.in);
		System.out.println("请选择手动输入还是机选(1:手动输入, 2:全部机选)：");
		int moa = scanner.nextInt();
		while(1> moa || 2< moa){
			System.out.println("输入有误，请重新输入!");
			scanner.hasNextInt();
			moa = scanner.nextInt();
		}
		return moa;
	}
	
	
	public void lotteryGroups(){
		Scanner scanner = new Scanner(System.in);		//在进行手动输入或者在机选后关闭scanner
		
		int moa = manualOrAuto(scanner);
		
		//进入手动输入号码代码
		if (1 == moa) {
			System.out.println("你选择了手动输入模式:");
			System.out.println("规则:输入0为自动机选");

			//0的个数
			int countZero =0;
			//多少倍
			int lotTimes = 0;
			for (int i = 0; i < lotteryBall.length; i++) {
				// 输入红球号码
				if (i < lotteryBall.length - 1) {
					System.out.println("请输入第" + (i + 1) + "个红球号码(1~33):");
					//获取控制台输入的值
					int next = scanner.nextInt();
					// 判断红球是否超出范围
					while (next < 0 || next > 33) {
						System.out.println("输入有误，请输入正确的号码：");
						// scanner.hasNextInt();
						next = scanner.nextInt();
					}
					lotteryBall[i] = next;
					if(0 == next){
						countZero++;
					}
				}
				// 输入蓝球号码
				else if (i == lotteryBall.length - 1) {
					System.out.println("请输入蓝球号码(1~16):");
					int next = scanner.nextInt();
					// 判断蓝球是否超出范围
					while (next < 0 || next > 16) {
						System.out.println("输入有误，请输入正确的号码：");
						// scanner.hasNextInt();
						next = scanner.nextInt();
					}
					lotteryBall[i] = next;
					if(0 == next){
						countZero++;
					}
				} else {
					System.out.println("手动输入异常错误!");
				}
				
			}
			
			
			
			//判断是否存在0的号码,有输入0时，判断是否进行多注下注
			//manualAndAutoRed(lotteryBall,scanner,countZero);
			if(0 == countZero){
				manualRed(lotteryBall);
				//询问是否添加倍数
				lotTimes = lotTimes(scanner);
				printNum(lotteryBall, lotTimes);
			}else {
				//得到机选多少注
				int groupNum = dupliManualAndAuto(scanner);
				
				if(1 == groupNum){				//1组手动+机选 的情况
					//询问是否添加倍数
					lotTimes = lotTimes(scanner);
					onceManualAndAutoRed(lotteryBall,countZero);
					printNum(lotteryBall, lotTimes);
					
				}else{					//多组手动+机选 的情况
					//询问是否添加倍数
					lotTimes = lotTimes(scanner);
					int[][] multiGroups = multiManualAndAutoRed(lotteryBall, groupNum,countZero);
					
					System.out.println("multiGroups的长度为:"+multiGroups.length);
					for(int i =0; i< multiGroups.length;i++){
						printNum(multiGroups[i], lotTimes);					//<----------------------打印结果不正常，为最后的数组
						System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝以上为外部引用打印值，不正常，只剩最后一个数组");
					}
				}
			}
			scanner.close();
		}
		
		//进入机选
		//确认要进行机选数目
		if( 2 == moa ){
			System.out.println("你选择了全部机选模式:");
			System.out.println("请输入进行机选的数目(1-99):");
			// Scanner scanner = new Scanner(System.in);
			int groupNum = scanner.nextInt();
			if (groupNum<=0 || groupNum >99) {
				System.out.println("输入有误，请重新输入!");
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
	
	//手动，没有机选的情况
	public int[] manualRed(int[] lotteryBall){
			int[] tempGroup = lotteryBall;
		
			//没有输入0，则只进行一次打印(单注倍数还没实现)
			setSequence(tempGroup);
			return tempGroup;
	}
	
	//手动，将剩余号码进行1次机选
	public int[] onceManualAndAutoRed(int[] tempGroup, int countZero){
		int [] tempball = new int[7];
		//排序成功
		setSequenceZeroLast(tempGroup);
		//将0的号码进行自动机选
		//将蓝球进行自动选号
		if(0 == tempGroup[tempGroup.length-1] && countZero == 1 ){
			tempGroup[tempGroup.length-1]=randBlue();
		}else{
			//剩余的红球进行选号
			System.out.println("进行剩余的自动选号");
			if(0 == tempGroup[tempGroup.length-1]){
				tempGroup[tempGroup.length-1]=randBlue();
			}
			//对红球进行自动选号
			Random random = new Random();
			for(int i=tempGroup.length-countZero-1; i < tempGroup.length-1; i++){
				//第五位随机
				tempGroup[i] = (int) random.nextInt(redPools) + 1;
				// 在自动生成时检查是否存在重复的号码
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

	
	//手动，多次剩余号码机选
	public int[][] multiManualAndAutoRed(int[] lotteryBall,int groupNum,int countZero){
			
		int[][] multiGroups = new int[groupNum][lotteryBall.length]; 
		for(int i=0; i<groupNum;i++){
			multiGroups[i] = onceManualAndAutoRed(lotteryBall,countZero);
			System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝以上为内部打印值");
			printNum(multiGroups[i], 1);
			System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝以上为内部打印值");
		}
		System.out.println("已经完成multiManualAndAutoRed");

		return multiGroups;
	}
	
	//自动，全机选 
	public int[] randRedAuto() {
		Random random = new Random();
		for (int i = 0; i < (lotteryBall.length - 1); i++) {
			lotteryBall[i] = (int) random.nextInt(redPools) + 1;
			// 在自动生成时检查是否存在重复的号码
			for (int j = 0; j < i; j++) {
				if (lotteryBall[j] == lotteryBall[i]) {
					j = 0;
					lotteryBall[i] = (int) (random.nextInt(redPools) + 1);
				}
			}
		}
		
		//对红球进行大小排序
		setSequence(lotteryBall);
		
		//将蓝球添加进行数组
		lotteryBall[lotteryBall.length-1] = randBlue();
		
		//printNum(lotteryBall);
		return lotteryBall;
	}

	
	//询问是否添加单注多倍
	public int lotTimes(Scanner scanner){
		System.out.println("请输入投入单注倍数(1-99):");
		int lotTimes = scanner.nextInt();
		while(lotTimes <1 ||lotTimes >99){
			System.out.println("输入有误，请输入1-99的倍数");
			lotTimes = scanner.nextInt();
		}
		return lotTimes;
	}
	
	// 将0数字放到最后面，从大到小。
	public void setSequenceZeroLast(int[] lotteryBall){
		for(int i = 0; i< lotteryBall.length-1; i++){
			//如果是0,将其放到最后面，如果不是0就什么都不做
			if(0 == lotteryBall[i]){
				for(int j= lotteryBall.length-2; j > (0+i);j--){ //最后的蓝球不动，不变化位置
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
	
	//对红球进行大小排序
	public void setSequence(int[] lotteryBall){
		
		// 将数字进行排列，从小到大。
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

	
	//进行组数确认
	public int dupliManualAndAuto(Scanner scanner){
		System.out.println("你有输入0，会进行机选 ，请输入机选组数(1-99)：");
		//获取组数
		int groupNum = scanner.nextInt();
		while(groupNum<1 || groupNum >99){
			System.out.println("输入有误，请输入正确组数:");
			//得到组数
			groupNum=scanner.nextInt();
		}
		return groupNum;
	}
	
	//进行蓝球的抽选,在1－16中抽选一个数字
	private int randBlue(){
		Random random = new Random();
		int blueBall = (int)random.nextInt(16)+1;
		return blueBall;
	}
	
	//打印数字
	private static void printNum(int[] balls, int lotTimes){
		for(int temp: balls){
			DecimalFormat df = new DecimalFormat("00");
			System.out.print(df.format(temp)+ "\t");
		}
		System.out.println("倍数:" + lotTimes);
	}
	
}