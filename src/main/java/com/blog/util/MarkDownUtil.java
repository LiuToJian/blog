package com.blog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
 * Markdown格式转换成HTML格式
 * 用于从数据库中读取博客内容时，将进行格式转换
 */
public class MarkDownUtil {

    /**
     * Markdown格式转换成HTML格式
     *
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /**
     * 增加扩展
     * 标题锚点，表格生成
     *
     * @param markdown
     * @return
     */
    public static String markdownToHtmlExtensions(String markdown) {
        /*把h标签生成ID*/
        Set<Extension> headingAnchorExtension = Collections.singleton(HeadingAnchorExtension.create());
        /*转换table的HTML*/
        List<Extension> tableExtension = Arrays.asList(TablesExtension.create());

        Parser parser = Parser.builder()
                .extensions(tableExtension).build();

        Node document = parser.parse(markdown);

        HtmlRenderer renderer = HtmlRenderer.builder().extensions(headingAnchorExtension)
                .extensions(tableExtension).attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext attributeProviderContext) {
                        return new CustomAttributeProvider();
                    }
                }).build();
        return renderer.render(document);
    }

    /*
     * 处理标签的属性
     * */
    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String s, Map<String, String> map) {
            if (node instanceof Link) {
                map.put("target", "_blank");
            }
            if (node instanceof TableBlock) {
                map.put("class", "ui celled table");
            }
        }
    }
}
