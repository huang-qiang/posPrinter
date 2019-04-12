package com.nodl.yct380b;

import com.nodl.yct380b.util.ArrayUtil;
import com.nodl.yct380b.util.BytesBuffer;

public class ESCPOSCode {

	public static final byte HT = 0x9; // 水平制表
	public static final byte LF = 0x0A; // 打印并换行
	public static final byte FF = 0x0C; //页进纸
	public static final byte ESC = 0x1B; // ESC码
	public static final byte GS = 0x1D;
	public static final byte FS = 0x1C;
	
	// 实时状态传送
	public static final byte[] DLE_EOT(int n) {
		return new byte[] { 0x10, 0x04, (byte) n };
	}
	
	// 实时响应主机请求
	public static final byte[] DLE_ENQ(int n) {
		return new byte[] { 0x10, 0x05, (byte) n };
	}
	
	// 设置右边间距为n点
	public static final byte[] ESC_SP(int n) {
		return new byte[] { ESC, ' ', (byte) n };
	}
	
	// 打印模式設置
	public static final byte[] ESC_PM(int n) {
		return new byte[] { ESC, '!', (byte) n };
	}
	
	// 设置绝对打印位置
	public static final byte[] ESC_PM(int nL, int nH) {
		return new byte[] { ESC, '$', (byte) nL,  (byte) nH};
	}
	
	//图形模式设定
	public static final byte[] IMAGE_PRINT(byte modole, byte nL, byte nH, byte[] bit_image) {
		BytesBuffer out = new BytesBuffer();
		out.write(new byte[] { ESC, '*', modole, (byte) nL, (byte) nH  });
		out.write(bit_image);// 位图信息
		return out.toByteArray();
	}	
	
	// 设置解除下划线
	public static final byte[] ESC_UL(int n) {
		return new byte[] { ESC, '-', (byte) n};
	}
	
	//选择缺省行距
	public static final byte[] ESC_LD = new byte[] { ESC, '2' };
	
	// 行距设置
	public static final byte[] ESC_SLD(int n) {
		return new byte[] { ESC, '3', (byte) n};
	}	
	
	//初始化
	public static final byte[] ESC_INIT = new byte[] { ESC, '@' };
	
	//水平制表位设置
	public static final byte[] ESC_D(byte[] ht_table) {
		BytesBuffer out = new BytesBuffer();
		out.write(new byte[] { ESC, 'D' });
		out.write(ht_table);
		out.write(new byte[] { 0x00 });
		return out.toByteArray();
	}		
	
	// 设置解除粗体打印
	public static final byte[] ESC_E(int n) {
		return new byte[] { ESC, 'E', (byte) n};
	}	
	
	// 打印并进纸n/203寸
	public static final byte[] ESC_J(int n) {
		return new byte[] { ESC, 'J', (byte) n};
	}	
	
	// 设置解除顺时针90旋转
	public static final byte[] ESC_V(int n) {
		return new byte[] { ESC, 'V', (byte) n};
	}	
	
	// 打印并退纸n/203寸
	public static final byte[] ESC_j(int n) {
		return new byte[] { ESC, 'j', (byte) n};
	}
	
	// 设置对齐方式
	public static final byte[] ESC_a(int n) {
		return new byte[] { ESC, 'a', (byte) n};
	}	
	
	// 选择打印纸传感器以输出缺纸
	public static final byte[] ESC_c3(int n) {
		return new byte[] { ESC, 'c', '3', (byte) n};
	}	
	
	// 选择打印纸传感器以停止打印
	public static final byte[] ESC_c4(int n) {
		return new byte[] { ESC, 'c', '4', (byte) n};
	}	
		
	// 允许／禁止按键开关操作
	public static final byte[] ESC_c5(int n) {
		return new byte[] { ESC, 'c', '5', (byte) n};
	}	
	
	// 打印并进纸n行
	public static final byte[] ESC_d(int n) {
		return new byte[] { ESC, 'd', (byte) n};
	}	
	
	//找黑标
	public static final byte[] FIND_BLACK = new byte[] { GS, FF };
	
	//设置黑标距离
	public static final byte[] ESC_SET_BLACK(int pL, int pH, int a, int m, int nL, int nH) {
		return new byte[] { GS, '(', 'F', (byte) pL, (byte) pH, (byte) a, (byte) m, (byte) nL, (byte) nH};
	}	
	
	//定制打印机控制值
	public static final byte[] ESC_SET_PRINTER_CONTROL_VALUE(int pL, int pH, int n, int m) {
		return new byte[] { GS, '(', 'M', (byte) pL, (byte) pH, (byte) n, (byte) m};
	}	
	
	// 选择裁纸模式并裁纸
	public static final byte[] ESC_CUTPAPER(int m) {
		return new byte[] { GS, 'V', (byte) m};
	}	
	
	//全切纸
	public static final byte[] ESC_FLL_CUTPAPER = new byte[] { ESC, 'i' };
	//半切纸
	public static final byte[] ESC_HALF_CUTPAPER = new byte[] { ESC, 'm' };	
	
	//选择中文字体和打印模式
	public static final byte[] FS_SELET_CHINESE_FONT_AND_PRINTMODE(int n) {
		return new byte[] { FS, '!', (byte) n};
	}	
	
	//进入汉字方式
	public static final byte[] FS_ENTER_CHINESE = new byte[] { FS, '&' };
	
	//退出汉字方式
	public static final byte[] FS_LEAVE_CHINESE = new byte[] { FS, '.' };
	
	//设置汉字左右间距
	public static final byte[] FS_SET_CHINESE_LR(int n1, int n2) {
		return new byte[] { FS, 'S', (byte) n1, (byte)n2};
	}	
	
	//设定二维码大小
	public static final byte[] GS_SET_QR_SIZE(int n) {
		return new byte[] { GS, 0x01, 0x03, (byte)n};
	}	
	
	//设定二维码纠错等级
	public static final byte[] GS_SET_QR_ERROR(int n) {
		return new byte[] { GS, 0x01, 0x04, (byte)n};
	}	
	
	//发送二维码数据
	public static final byte[] GS_SEND_QR_DATA(byte nl, byte nh, byte[] qr_data) {
		BytesBuffer out = new BytesBuffer();
		out.write(new byte[] { GS, 0x01, 0x01, (byte) nl, (byte) nh  });
		out.write(qr_data);// 位图信息
		return out.toByteArray();
	}		
	
	//打印二维码
	public static final byte[] GS_PRINT_QR = new byte[] { GS, 0x01, 0x02 };
	
	//设定字符大小
	public static final byte[] GS_SET_CHAR_SIZE(int n) {
		return new byte[] { GS, '!', (byte)n};
	}	
	
	//条码HRI设置
	public static final byte[] GS_SET_BAR_HRI(int n) {
		return new byte[] { GS, 'H', (byte)n};
	}	
	
	//设置打印区左边界
	public static final byte[] GS_SET_PRINT_LEFT(int nl, int nh) {
		return new byte[] { GS, 'L', (byte) nl, (byte)nh};
	}		
	
	//设置打印区宽度
	public static final byte[] GS_SET_PRINT_WIDTH(int nl, int nh) {
		return new byte[] { GS, 'W', (byte) nl, (byte)nh};
	}		
		
	//允许/禁止自动状态回复(ASB)
	public static final byte[] GS_SET_AUTO_RESPONSE(int n) {
		return new byte[] { GS, 'a', (byte) n};
	}		
	
	//参数传送
	public static final byte[] GS_SEND_PARAMETER(int n) {
		return new byte[] { GS, 'r', (byte) n};
	}	
	
	//设置条码高度
	public static final byte[] GS_SET_BAR_HEIGHT(int n) {
		return new byte[] { GS, 'h', (byte) n};
	}	
		
	//打印条码
	public static final byte[] GS_PRINT_BAR(byte n, byte m, byte[] bar_data) {
		BytesBuffer out = new BytesBuffer();
		out.write(new byte[] { GS, 'k', (byte) n, (byte) m  });
		out.write(bar_data);
		return out.toByteArray();
	}	
	
	//设置条码宽度
	public static final byte[] GS_SET_BAR_WIDTH(int n) {
		return new byte[] { GS, 'w', (byte) n};
	}	
	
	//选择切纸模式
	public static final byte[] ESC_SET_CUT_MODE(int m, int n) {
		return new byte[] { ESC, 'N', (byte) m, (byte)n};
	}	
	
	//黑标模式设置
	public static final byte[] GS_SET_BLACK_MODE(int n) {
		return new byte[] { GS, ':', (byte) n};
	}	
	
	//选择黑标
	public static final byte[] GS_SELECT_BLACK(int n) {
		return new byte[] { GS, 'N', (byte) n};
	}		
	
	
	
	
	
	
	public static final byte[] BEL = new byte[] { ESC, 0x7 };

	public static final byte[] RESET = new byte[] { ESC, '@' };

	public static final byte[] STATUS = new byte[] { ESC, 'v' };

	/* 初始化打印机 ECS @ */
	public static final byte[] ESC_RESETPRINTER = new byte[] { ESC, 0x40 };

	/* 靠左打印命令 */
	public static final byte[] ESC_ALIGN_LEFT = new byte[] { ESC, 'a', 0x00 };

	/* 靠右打印命令 */
	public static final byte[] ESC_ALIGN_RIGHT = new byte[] { ESC, 'a', 0x02 };

	/* 居中打印命令 */
	public static final byte[] ESC_ALIGN_CENTER = new byte[] { ESC, 'a', 0x01 };

	/* 取消字体加粗 */
	public static final byte[] ESC_CANCEL_BOLD = new byte[] { ESC, 0x45, 0 };

	/* 设置字体加粗 */
	public static final byte[] ESC_SET_BOLD = new byte[] { ESC, 0x45, 0x01 };

	/* 取消字体下划线 */
	public static final byte[] FS_CANCEL_UNDERLINE = new byte[] { FS, 0x2D, 0x00 };

	/* 设置字体下划线 */
	public static final byte[] FS_SET_UNDERLINE = new byte[] { FS, 0x2D, 0x01 };

	/** 字体大小 */
	public static final byte[] ESC_FONT_SIZE_MAX = new byte[] { GS, '!', 0x22 };
	public static final byte[] ESC_FONT_SIZE_MID = new byte[] { GS, '!', 0x11 };
	public static final byte[] ESC_FONT_SIZE_MIN = new byte[] { GS, '!', 0x00 };

	// // 走纸n行
	public static final byte[] ESC_FORWARD_LINE(int n) {
		return new byte[] { ESC, 'd', (byte) n };
	}

	// 切纸
	public static final byte[] ESC_CUTPAPER = new byte[] { ESC, 0x6D };

	// 走纸n点，一般8点一个像素，本指令执行后走纸后将移动到下一行的起始位置
	public static final byte[] ESC_FORWARD_DOT(int n) {
		return new byte[] { ESC, 'J', (byte) n };
	}

	/**
	 * 打印图片命令
	 * 
	 * @param modole
	 * @param xL
	 * @param xH
	 * @param yL
	 * @param yH
	 * @param bit_image
	 * @return
	 */
	public static final byte[] IMAGE_PRINT(byte modole, byte xL, byte xH, byte yL, byte yH, byte[] bit_image) {
		BytesBuffer out = new BytesBuffer();
		out.write(new byte[] { 0x1d, 0x76, 0x30 }); // 基本指令
		out.write(new byte[] { modole, xL, xH, yL, yH });// 打印设置
		out.write(bit_image);// 位图信息
		return out.toByteArray();
	}

	/**
	 * 设置制表位置
	 * 
	 * @param positions
	 * @return
	 */
	public static final byte[] SET_TABLE_CELL_POSITION(byte[] positions) {
		byte[] command = new byte[] { 0x1b, 0x44 };
		byte[] end_mark = new byte[] { 0x00 };
		if (null != positions) {
			return ArrayUtil.contact(command, positions, end_mark);
		} else {
			return ArrayUtil.contact(command, end_mark);
		}
	}

	/**
	 * 取消制表位置
	 * 
	 * @return
	 */
	public static final byte[] CANCLE_TABLE_CELL_POSITION_SETTING() {
		return SET_TABLE_CELL_POSITION(null);
	}
}