package xpath.abczww.bjcar.crawler.core;

import java.util.List;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXNode;

public class UtilTools {
	
	public static final String getContentByXPath(JXNode node, String xPath, String attr) throws XpathSyntaxErrorException {
		String reValue = "";
		List<JXNode> eles = node.sel(xPath);
		if(eles.size() >0){
			if(attr!=null && !attr.trim().equals("") && attr.trim().length()>1){
				reValue = eles.get(0).getElement().attr("href");
			}else{
				reValue = eles.get(0).getElement().text();
			}
		}
		return reValue;
	}

}
