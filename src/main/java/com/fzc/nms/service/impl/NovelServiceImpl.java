package com.fzc.nms.service.impl;

import com.fzc.nms.domain.Novel;
import com.fzc.nms.mapper.NovelMapper;
import com.fzc.nms.service.NovelService;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2021/12/12/0012
 * NovelServiceImpl
 *
 * @author 帅帅付
 */
@Service
public class NovelServiceImpl implements NovelService {
    @Resource
    NovelMapper novelMapper;

    @Override
    public int add(Novel novel) {
        return novelMapper.insertNovel(novel);
    }

    @Override
    public int delete(Integer id) {
        return novelMapper.deleteNovelById(id);
    }

    @Override
    public int delete(Integer[] ids) {
        return novelMapper.deleteNovelByIds(ids);
    }

    @Override
    public int delete(String username) {
        return novelMapper.deleteNovelByUsername(username);
    }

    @Override
    public int modify(Novel novel) {
        return novelMapper.modifyNovel(novel);
    }

    @Override
    public Novel query(Integer id) {
        return novelMapper.selectNovelById(id);
    }

    @Override
    public List<Novel> query(String username, Integer startIndex,
                             Integer length) {
        return novelMapper.selectNovelByUsername(username, startIndex, length);
    }

    @Override
    public int queryTotal(String username) {
        return novelMapper.selectTotalByUsername(username);
    }

    @Override
    /**
     * 目前没处理图片。
     */
    public String parseWordDocx(File file) {
        //1. 创建document对象
        XWPFDocument document = null;
        //2. 创建xml-html转换器对象
        XHTMLConverter xhtmlConverter = null;
        //3. 创建一个输出流来接收转换后的html文本
        StringWriter writer = null;
        //4. 创建一个输入流读取临时文件
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            //1. 创建document对象
            document = new XWPFDocument(inputStream);
            //2. 创建xml-html转换器对象
            xhtmlConverter = new XHTMLConverter();
            //3. 创建一个输出流来接收转换后的html文本
            writer = new StringWriter();
            //4. 转换
            xhtmlConverter.convert(document, writer, XHTMLOptions.getDefault());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return format(writer.toString());
    }

    @Override
    public String parseWordDoc(File file) {
        HWPFDocument document = null;
        Document html = null;
        WordToHtmlConverter converter = null;
        StringWriter writer = null;
        Transformer transformer = null;
        FileInputStream inputStream = null;
        try {

            inputStream = new FileInputStream(file);
            document = new HWPFDocument(inputStream);
            html =
                    XMLHelper.getDocumentBuilderFactory().newDocumentBuilder().newDocument();

            converter = new WordToHtmlConverter(html);
            converter.processDocument(document);

            transformer = TransformerFactory.newInstance().newTransformer();
            writer = new StringWriter();
            transformer.transform(new DOMSource(converter.getDocument()),
                    new StreamResult(writer));


        } catch (IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return format(writer.toString());
    }

    @Override
    public String format(String html) {
        /*去回车，去换行*/
        html = html.trim();
        html = html.replaceAll("\n", "");
        html = html.replaceAll("\r", "");

        /*匹配完body需要去除body标签*/
        String patternString = "<body[^>]*>[\\S\\s]*?</body>";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(html);
        System.out.println("NovelServiceImpl.getBody方法：是否找到" + matcher.find());

        String content = matcher.group();
        String patBodyBeg = "<body[^>]*>";
        String patBodyEnd = "</body>";

        /*去除前标签*/
        pattern = Pattern.compile(patBodyBeg);
        matcher = pattern.matcher(content);
        content = matcher.replaceAll("");

        /*去除尾标签*/
        pattern = Pattern.compile(patBodyEnd);
        matcher = pattern.matcher(content);
        content = matcher.replaceAll("");

        return content;
    }

    @Override
    public String parseTxt(File file) {
        /*由于要经常拼接字符串，用StringBuilder会比较好*/
        StringBuilder html = new StringBuilder();
        BufferedReader reader = null;
        //1. 用一个BufueredReader读取file
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            //2. 读一行就放到一个<p></p>里面
            while ((line = reader.readLine()) != null) {
                html.append("<p>");
                html.append(line);
                html.append("</p>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return html.toString();
    }

    @Override
    public String wrapNoBodyHtml(String html) {
        StringBuilder wraped = new StringBuilder();
        wraped.append("<html><head>");
        wraped.append("<META content=\"charset=UTF-8\"></META>");
        wraped.append("</head><body>");
        wraped.append(html);
        wraped.append("</body></html>");

        return wraped.toString();
    }

    @Override
    public Boolean parseHtmlToDocx(String novel,
                                   OutputStream responseOutputStream) {
        //把数据库里存储的包起来就不会报错了，或者用个div套起来
        //不能理解，明明上面说的The fragment should be one or more block level objects
        novel = wrapNoBodyHtml(novel);
        try {
            //1. 实例化 docx4j 对象
            WordprocessingMLPackage mlPackage =
                    WordprocessingMLPackage.createPackage();
            XHTMLImporterImpl importer = new XHTMLImporterImpl(mlPackage);
            //加载html文档
            mlPackage.getMainDocumentPart().getContent().addAll(importer.convert(novel, null));
            //用输出流来保存
            mlPackage.save(responseOutputStream);
        } catch (Docx4JException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean parseHtmlToDoc(String novel,
                                          OutputStream responseOutputStream) {
        novel = wrapNoBodyHtml(novel);
        ByteArrayInputStream bais = null;
        POIFSFileSystem fileSystem = null;
        try {
            bais = new ByteArrayInputStream(novel.getBytes());
            fileSystem = new POIFSFileSystem();
            DirectoryEntry directoryEntry = fileSystem.getRoot();
            directoryEntry.createDocument("WordDocument", bais);
            fileSystem.writeFilesystem(responseOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } if (bais != null) {
            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public Boolean parseHtmlToTxt(String novel,
                                          OutputStream responseOutputStream) {
        /*
          就这样吧，不然根据是不是块标签来判断要不要换行贼麻烦……
          要是复杂点有什么float样式就更是了
          */
        String text = novel.replaceAll("\\<.*?\\>", "\n");
        try {
            responseOutputStream.write(text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
