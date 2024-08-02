package cn.sx.hu.star.id.springboot.example.controller;

import cn.sx.hu.star.id.core.exception.IdException;
import cn.sx.hu.star.id.core.factory.IdFactory;
import cn.sx.hu.star.id.springboot.example.controller.vo.IdVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ID获取接口
 *
 * <p>更多内容参看<a href="https://hu.sx.cn"><b>老胡爱分享</b></a>
 * @author hu
 */
@RestController
@RequestMapping("id")
public class IdController {

    @GetMapping
    @ResponseBody
    public ResponseEntity<IdVO> generate() throws IdException {
        IdVO vo = new IdVO();
        vo.setLongValue(IdFactory.getInstance().id().getLong());
        vo.setStringValue(IdFactory.getInstance().id().getString());
        return ResponseEntity.ok(vo);
    }

}
