package com.cnki.ksp.helper;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLCleanHelper {

	/** refex for script. */
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
	/** regex for style. */
	private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
	/** regex for html. */
	private static final String regEx_html = "<[^>]+>";
	/** regex for space. */
	private static final String regEx_space = "\\s*|\t|\r|\n";

	/**
	 * remove all html tag from content.
	 * 
	 * @param htmlStr,
	 *            the content of the html.
	 * @return the cleaned Html tag.
	 */
	public static String removeHTML(String htmlStr) {
		// remove all script tags.
		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll("");

		// remove all style tags.
		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll("");

		// remove all html tags
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll("");

		// remove blank \t\n.
		Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll("");

		// remove all &nbsp;
		htmlStr = htmlStr.replaceAll("&nbsp;", "");

		// the cleaned html content.
		return htmlStr.trim();
	}

	/**
	 * clean/remove/delete the style and script tag.
	 * 
	 * @param htmlStr
	 * @return the cleaned html content.
	 */
	public static String removeSytleAndScript(String htmlStr) {
		// clean/remove all script tags
		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll("");

		// clean/remove all style tags.
		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll("");

		// return the cleaned html content.
		return htmlStr.trim();
	}

	/**
	 * get all images' src
	 * 
	 * @param htmlStr
	 *            the content of the html;
	 * @return all images' src.
	 */
	public static Set<String> getImgSrcs(String htmlStr) {
		Set<String> pics = new HashSet<>();
		String img = "";
		Pattern p_image;
		Matcher m_image;
		String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			// get <img /> data
			img = m_image.group();
			// get <img> src data
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
			while (m.find()) {
				pics.add(m.group(1));
			}
		}
		return pics;
	}

}
