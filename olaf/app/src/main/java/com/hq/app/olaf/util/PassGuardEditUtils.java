package com.hq.app.olaf.util;


import cn.passguard.PassGuardEdit;

public class PassGuardEditUtils {
	public static String license = "bnhSOTBsem40TXhDNklObW9mWDlrcWFjLzlJc0Jxc0tpNFlJc2k1K0FaK0FOcitTaUR6Y1ZmZVZWdTFkQUJqaGRSdlg5azNGNWF1b0xCYTY2RFRSUE9FRkhkV2NwZVZFK2ZEV1VBNkwzcytHV2JIbTk3cU5xWDRLcTNpcktpQm9QMSt3dGNjUjVjVTVGaThqTmYrUVRGeTVpOVNEd3ducXpMODRxZE9mUjVBPXsiaWQiOjAsInR5cGUiOiJwcm9kdWN0IiwicGFja2FnZSI6WyJjb20udHouZml2ZWNvdXJpZXIiXSwiYXBwbHluYW1lIjpbIuaCpuWKnuWFrCJdLCJwbGF0Zm9ybSI6Mn0";

	public static String initPwd(final PassGuardEdit edit, String cipherKey) {
		PassGuardEdit.setLicense(Const.PASS_GUARD_EDIT_LICENSE);
		if (cipherKey == null) {
			cipherKey = StringUtil.generateString(32);
		}
		edit.setCipherKey(cipherKey);
		edit.setPublicKey("3081890281810092d9d8d04fb5f8ef9b8374f21690fd46fdbf49b40eeccdf416b4e2ac2044b0cfe3bd67eb4416b26fd18c9d3833770a526fd1ab66a83ed969af74238d6c900403fc498154ec74eaf420e7338675cad7f19332b4a56be4ff946b662a3c2d217efbe4dc646fb742b8c62bfe8e25fd5dc59e7540695fa8b9cd5bfd9f92dfad009d230203010001");
		edit.setEccKey("cbe6fbafb20fb69fa035fdeb43c6e11065e28edf9d9dc1b0c008571b3657f432|bf27b68d1c7b354e0abc391bdb96e5cb2ff860b97c200e6694f885f6f5bf8973");
		edit.setMaxLength(16);
		edit.setButtonPress(true);
		edit.setReorder(PassGuardEdit.KEY_NONE_CHAOS);
		edit.setInputRegex("[a-zA-Z0-9@_\\.]");
		edit.initPassGuardKeyBoard();
		return cipherKey;
	}

	/**
	 * 6.getPassLevel，返回一个包含两个int的数组，int[0]返回当前输入框内容的组成结构： 完全为空，返回0。
	 * 仅有数字，字符或特殊符号为1。例：”1234””abcd”“%#@!” 有两种组合返回2。例:”12bd”“12@#”“ab@#”
	 * 有三种组合返回3。例：”1@b”“1@2ab3”
	 * Int[1]返回判断当前输入框内容是否为弱密码。若内容为8位以下且是连续字符则为弱密码，返回1。否则并非弱密码，返回0。
	 * 
	 * @param edit
	 * @return
	 */
	public static boolean isDigitAndLetter(final PassGuardEdit edit) {
		int[] level = edit.getPassLevel();
		if (level[0] > 1) {
			return true;
		}
		return false;
	}

}
