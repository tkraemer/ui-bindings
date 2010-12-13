package com.rcpcompany.uibindings.internal.clipboardconverters;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;

/**
 * {@link IClipboardConverter} for Comma-Separated-Values.
 * <p>
 * Very common interchange format.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class CSVConverter implements IClipboardConverter {
	private final String myName;
	private final String mySeparator;

	public CSVConverter(String name, String separator) {
		myName = name;
		mySeparator = separator;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return myName;
	}

	@Override
	public String[][] convert(Clipboard clipboard) {
		String content = (String) clipboard.getContents(TextTransfer.getInstance());
		if (content == null) return null;
		while (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}
		content = content.replace("\r", "");
		final String[] lines = content.split("\n");
		final int noLines = lines.length;
		final String[][] result = new String[noLines][0];
		for (int i = 0; i < lines.length; i++) {
			result[i] = lines[i].split(mySeparator);
		}
		return result;
	}

}
