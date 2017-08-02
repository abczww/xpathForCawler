package com.cnki.ksp.core;

import java.util.List;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;

public class XPathUtilTools {

	private JXDocument xdoc;

	public XPathUtilTools(JXDocument xdoc) {
		this.xdoc = xdoc;
	}

	public String getContentByXPath(JXNode node, String xPath, String attr) throws XpathSyntaxErrorException {
		String reValue = "";
		List<JXNode> eles = node.sel(xPath);
		if (eles.size() > 0) {
			if (attr != null && !attr.trim().equals("") && attr.trim().length() > 1) {
				reValue = eles.get(0).getElement().attr("href");
			} else {
				if (null == eles.get(0).getElement()) {
					reValue = eles.get(0).getTextVal();
				} else {
					reValue = eles.get(0).getElement().text();
				}
			}
		}
		return reValue;
	}

	public String getContentByXPath(String xPath) throws XpathSyntaxErrorException, StyleChangedException {
		if (null == xPath || "".equals(xPath.trim())) {
			return null;
		}
		List<JXNode> rs = xdoc.selN(xPath);
		String content = getContentByNodeList(rs);
		if (null == content) {
			throw new StyleChangedException("The xpath: " + xPath + " changed");
		}
		return content;
	}

	private String getContentByNodeList(List<JXNode> rs) {
		if (rs.size() > 0) {
			JXNode node = rs.get(0);
			String content = null;
			if (null == node.getElement()) {
				content = node.getTextVal();
			} else {
				content = node.getElement().html();
			}
			return content;
		}
		return null;
	}

}
