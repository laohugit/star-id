package cn.sx.hu.star.id.xml.registry;

import cn.sx.hu.star.id.core.constant.GlobalConstant;
import cn.sx.hu.star.id.xml.constant.FileConstant;
import cn.sx.hu.star.id.core.constant.Env;
import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.core.io.ResourceLoader;
import cn.sx.hu.star.id.core.model.IdDataCenter;
import cn.sx.hu.star.id.core.model.IdLocation;
import cn.sx.hu.star.id.core.model.IdRegistryContext;
import cn.sx.hu.star.id.core.model.IdWorker;
import cn.sx.hu.star.id.core.specs.Registry;
import cn.sx.hu.star.id.core.specs.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * XML注册器
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
public class XmlRegistry implements Registry {

    private final Logger logger = LoggerFactory.getLogger(XmlRegistry.class);

    private final Map<String, IdLocation> locationMap = new HashMap<>();

    @Override
    public void init(IdRegistryContext context) throws IdException {
        String filename = FileConstant.DEFAULT_XML_FILE_NAME;
        if(!Env.NONE.equals(context.getEnv())) {
            filename = String.format(FileConstant.ENV_XML_FILE_NAME, context.getEnv().getCode());
        }
        if(logger.isDebugEnabled()) {
            logger.debug("{} init-filename:{}", GlobalConstant.DEFAULT_ID_DISPLAY_NAME, filename);
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is =  ResourceLoader.getResourceAsStream(filename);
            Document document = builder.parse(is);
            Node rootNode = getRoot(document.getElementsByTagName("id"));
            convert(getDatacenters(rootNode));
            if(logger.isDebugEnabled()) {
                logger.debug("{} init-locationMap:{}", GlobalConstant.DEFAULT_ID_DISPLAY_NAME, locationMap);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new IdException(e);
        }
    }

    private Node getRoot(NodeList list) {
        return list.item(0);
    }

    private List<IdDataCenter> getDatacenters(Node rootNode) {
        NodeList datacenterNodes = rootNode.getChildNodes();
        List<IdDataCenter> idDataCenters = new ArrayList<>();
        for (int i = 0; i <datacenterNodes.getLength() ; i++) {
            if (datacenterNodes.item(i).getNodeType()== Node.ELEMENT_NODE) {
                IdDataCenter idDatacenter = new IdDataCenter();
                Node datacenterNode = datacenterNodes.item(i);
                NamedNodeMap namedNodeMap = datacenterNode.getAttributes();
                Node codeAttr = namedNodeMap.getNamedItem(FileConstant.NODE_NAME_CODE);
                Node groupAttr = namedNodeMap.getNamedItem(FileConstant.NODE_NAME_GROUP);
                if(Objects.nonNull(codeAttr)) {
                    idDatacenter.setCode(codeAttr.getNodeValue());
                }
                if(Objects.nonNull(groupAttr)){
                    idDatacenter.setGroup(groupAttr.getNodeValue());
                }
                idDatacenter.setWorkers(getWorkers(datacenterNode));
                idDataCenters.add(idDatacenter);
            }
        }
        return idDataCenters;
    }

    private List<IdWorker> getWorkers(Node datacenterNode) {
        NodeList workerNodes = datacenterNode.getChildNodes();
        List<IdWorker> idWorkers = new ArrayList<>();
        for (int i = 0; i < workerNodes.getLength(); i++) {
            if (workerNodes.item(i).getNodeType()== Node.ELEMENT_NODE) {
                IdWorker idWorker = new IdWorker();
                Node workerNode = workerNodes.item(i);
                NamedNodeMap namedNodeMap = workerNode.getAttributes();
                Node ipAttr = namedNodeMap.getNamedItem(FileConstant.NODE_ATTR_IP);
                Node portAttr = namedNodeMap.getNamedItem(FileConstant.NODE_ATTR_PORT);
                if(Objects.nonNull(ipAttr)) {
                    idWorker.setIps(analysisIp(ipAttr.getNodeValue()));
                }
                if(Objects.nonNull(portAttr)){
                    idWorker.setPort(Integer.parseInt(portAttr.getNodeValue()));
                }
                idWorkers.add(idWorker);
            }
        }
        return idWorkers;
    }


    private List<String> analysisIp(String ipRawValue) {
        if(ipRawValue.contains(FileConstant.SYMBOL_COMMA)) {
            return Arrays.stream(ipRawValue.split(FileConstant.SYMBOL_COMMA)).collect(Collectors.toList());
        }else {
            return Collections.singletonList(ipRawValue);
        }
    }

    private void convert(List<IdDataCenter> idDataCenters) {
         for(int i = 0; i < idDataCenters.size(); i++) {
             IdDataCenter idDatacenter = idDataCenters.get(i);
             List<IdWorker> idWorkers = idDatacenter.getWorkers();
             for(int j = 0; j < idWorkers.size(); j++) {
                 IdWorker idWorker = idWorkers.get(j);
                 construct(i, j, idWorker.getPort(), idWorker.getIps());
             }
         }
    }

    private void construct(long i, long j, int port, List<String> ips) {
        for(String ip : ips) {
            locationMap.put(getServiceAddress(ip, port), new IdLocation(i, j));
        }
    }

    @Override
    public IdLocation register(String ip, int port) throws IdException {
        return locationMap.get(getServiceAddress(ip, port));
    }

    @Override
    public boolean available(IdGenerator generator) {
        return true;
    }

}
