package com.jq.distribution.controller.api;

import com.jq.distribution.service.ExampleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@Slf4j
@RestController
@RefreshScope
public class DemoController {

    @Value("${timeout:100}")
    private int timeout;

    @Value("${dubbo.config-center.address:no}")
    private String address;

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @Value("${foo:null}")
    private String foo;

    @Resource
    ExampleInnerService exampleInnerService;

    @GetMapping("/index")
    public String index(@RequestParam String echo){
        log.info("params echo = {}", echo);
        return exampleInnerService.echo(echo);
    }

    @GetMapping("/nacos")
    public boolean nacos() {
        return this.useLocalCache;
    }

    @GetMapping("/foo")
    public String foo() {
        return foo;
    }

    @GetMapping("/core")
    public String core() {
        int core = Runtime.getRuntime().availableProcessors();
        return String.valueOf(core);
    }

//    @GetMapping("page")
//    public ModelAndView page(){
//        return new ModelAndView("websocket");
//    }
//
//    @RequestMapping("/push/{toUserId}")
//    public ResponseEntity<String> pushToWeb(String message, @PathVariable String toUserId) throws IOException {
//        WebSocketServer.sendInfo(message,toUserId);
//        return ResponseEntity.ok("MSG SEND SUCCESS");
//    }

}