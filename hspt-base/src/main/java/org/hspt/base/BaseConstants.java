package org.hspt.base;

/**
 * <b> 全局常量信息 </b>
 * <p>
 * 功能描述:全局常量及通用方法类
 * <p>
 * LINUX生成秘钥方法
 * <p>
 * <p>
 * #生成1024字节秘钥
 * openssl genrsa -out rsa_private_key.pem 1024
 * #生成公钥
 * openssl rsa -in rsa_private_key.pem -out rsa_public_key.pem -pubout
 * #如果要使用PKCS8编码执行下面语句
 * openssl pkcs8 -topk8 -in rsa_private_key.pem -out pkcs8_rsa_private_key.pem -nocrypt
 * <p>
 * #查看秘钥
 * <p>
 * cat rsa_private_key.pem
 * cat rsa_public_key.pem
 * cat pkcs8_rsa_private_key.pem
 *
 * </p>
 */
public class BaseConstants {


    /**
     * token存储字段名
     */
    public static final String TOKEN_KEY = "_ImpToken_";

    /**
     * 外部服务调用授权
     */
    public static final String OTHER_TOKEN_KEY = "_OtherToken_";

    /**
     * 外部调用传输消息ID
     */
    public static final String OTHER_MESSAGE_ID = "_OtherMessageId_";

    /**
     * 密钥存储字段
     */
    public static final String SECRET_KEY = "_secretKey_";

    /**
     * 基础包名
     */
    public static final String BASE_PACKAGE = "org.hspt";

    /**
     * 需要构建API对象的基本包路径
     */
    public static final String CONTROLLER_BASE_PATH = "org.hspt.controller";

    /**
     * 内部传输消息ID
     */
    public static final String GLOBAL_KEY = "_MessageId_";

    /**
     * 有管理权限的角色类型,多角色用英文逗号隔开
     */
    public static final String ADMIN_ROLE_TYPE = "SYS_ADMIN";

    /**
     * 用户TOKEN列表
     */
    public static final String USER_TOKEN = "UserToken";

    /**
     * TOKEN来源版本
     */
    public static final String TOKEN_VERSION = "TokenVersion";

    /**
     * TOKEN来源平台
     */
    public static final String TOKEN_PLATFORM = "TokenPlatform";


    /**
     * 全访问角色类型
     */
    public static final int ALL_USER_TYPE = -1;

    /**
     * 全访问用户角色名称
     */
    public static final String ALL_USER_NAME = "ALL_USER";


    /**
     * 系统管理员角色类型
     */
    public static final int SYS_ADMIN_TYPE = 0;

    /**
     * 系统管理员角色名称
     */
    public static final String SYS_ADMIN_NAME = "SYS_ADMIN";

    /**
     * 组织管理员角色类型
     */
    public static final int GROUP_ADMIN_TYPE = 1;

    /**
     * 组织管理员角色名称
     */
    public static final String GROUP_ADMIN_NAME = "GROUP_ADMIN";


    /**
     * 后台注册用户类型
     */
    public static final int GEN_USER_TYPE = 2;

    /**
     * 后台注册角色名称
     */
    public static final String GEN_USER_NAME = "GEN_USER";

    /**
     * 开放注册用户角色类型
     */
    public static final int PUB_USER_TYPE = 3;

    /**
     * 开发注册用户角色名称
     */
    public static final String PUB_USER_NAME = "PUB_USER";


    /**
     * 第三方接入角色类型
     */
    public static final int OTHER_USER_TYPE = 4;

    /**
     * 第三方接入角色名称
     */
    public static final String OTHER_USER_NAME = "OTHER_USER";


    /**
     * 开发人员角色类型
     */
    public static final int DEV_USER_TYPE = 5;

    /**
     * 开发人员角色名称
     */
    public static final String DEV_USER_NAME = "SYS_DEV";


    /**
     * 运维人员角色类型
     */
    public static final int OPER_USER_TYPE = 6;

    /**
     * 运维人员角色名称
     */
    public static final String OPER_USER_NAME = "SYS_OPER";


    /**
     * 数据状态 正常
     */
    public static final int DATA_STATUS_OK = 0;

    /**
     * 数据状态 删除
     */
    public static final int DATA_STATUS_DEL = 1;

    /**
     * 数据状态 封存
     */
    public static final int DATA_STATUS_CLOSE = 2;

    /**
     * 逻辑判断 Y
     */
    public static final String TRUE = "Y";

    /**
     * 逻辑判断 N
     */
    public static final String FALSE = "N";


    /**
     * 默认公钥
     */
    public static final String DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJ/rx/UDynjkQhSyTACVy3sBsT\n" +
            "hEwH0p+CUmOg9PPeBrQeCr4TlL6hIvIInu+EMtFUAE1nifkNcCj5jRk2lqGFQAZy\n" +
            "n5bTOvmK1cBWDLYYdBBiwFo3qad7sUzf1aR/gSL72qlarL8c9tMAuMsnPpXO8SKc\n" +
            "++Yc6htceYzLvAUO4wIDAQAB\n";

    /**
     * 默认私钥
     */
    public static final String DEFAULT_PRIVATE_KEY = "MIICXAIBAAKBgQDJ/rx/UDynjkQhSyTACVy3sBsThEwH0p+CUmOg9PPeBrQeCr4T\n" +
            "lL6hIvIInu+EMtFUAE1nifkNcCj5jRk2lqGFQAZyn5bTOvmK1cBWDLYYdBBiwFo3\n" +
            "qad7sUzf1aR/gSL72qlarL8c9tMAuMsnPpXO8SKc++Yc6htceYzLvAUO4wIDAQAB\n" +
            "AoGABoM5Dp3GptxWZABpOevTWnTKGrH8RZBL5kbDwY/EQRdUPVe5UyZLkyRuS0rc\n" +
            "jBWbXCr6U+lF52IDYDBlbLYklPmWmYvV2tP/mRul6tTv2PSidCq8FY3GbRx1510t\n" +
            "1txvaW8rDbGvKPhYEN1ZK/Fu8gWVm2WrGjns37RA4a2tSTkCQQDj2meUg/mdo0/A\n" +
            "pTHl2+YWEaTRJ8kIC4X+NDApUnD7rpUzVpLkxiTqEw4da7PzzkYGq0ZUCDdsp51C\n" +
            "Wt2kXhHVAkEA4vKZd/FzvcEykmCBN15EuWrWBXWEVcTNfWxN5CXzl3lDU8nkZ8sX\n" +
            "2jlRyM0uZySlzsRGJTOQzKzbENbUdglB1wJAHrW0GjdDcHSvbhaVyBZAR58vKKWF\n" +
            "Gdl7wMxF5XvgEHvyTg4QFFeEaCwTTfHTOjwGAiYZf3/wAGwC7QuULfAj1QJAMW7M\n" +
            "VN7n6gYiANY6bxf+ejC2K3w8df4nEFG96ZsGoxDLxH1uXdlT+bmQgd+HYvoNj6sa\n" +
            "6FGCEcL4+IMCxi8gAwJBAIMSxojifE+4R74tSTq2HdX+1kC8h4kUYgSxEJZWl3Az\n" +
            "FG+9BCY9ARy1Y2Pq6qhBUYmgAuXAX0UA0dz2aeZqQrc=\n";

    /**
     * 如果使用AOP拦截，则必须使用此私钥进行解密
     */
    public static final String DEFAULT_PKCS8_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMn+vH9QPKeORCFL\n" +
            "JMAJXLewGxOETAfSn4JSY6D0894GtB4KvhOUvqEi8gie74Qy0VQATWeJ+Q1wKPmN\n" +
            "GTaWoYVABnKfltM6+YrVwFYMthh0EGLAWjepp3uxTN/VpH+BIvvaqVqsvxz20wC4\n" +
            "yyc+lc7xIpz75hzqG1x5jMu8BQ7jAgMBAAECgYAGgzkOncam3FZkAGk569NadMoa\n" +
            "sfxFkEvmRsPBj8RBF1Q9V7lTJkuTJG5LStyMFZtcKvpT6UXnYgNgMGVstiSU+ZaZ\n" +
            "i9Xa0/+ZG6Xq1O/Y9KJ0KrwVjcZtHHXnXS3W3G9pbysNsa8o+FgQ3Vkr8W7yBZWb\n" +
            "ZasaOezftEDhra1JOQJBAOPaZ5SD+Z2jT8ClMeXb5hYRpNEnyQgLhf40MClScPuu\n" +
            "lTNWkuTGJOoTDh1rs/PORgarRlQIN2ynnUJa3aReEdUCQQDi8pl38XO9wTKSYIE3\n" +
            "XkS5atYFdYRVxM19bE3kJfOXeUNTyeRnyxfaOVHIzS5nJKXOxEYlM5DMrNsQ1tR2\n" +
            "CUHXAkAetbQaN0NwdK9uFpXIFkBHny8opYUZ2XvAzEXle+AQe/JODhAUV4RoLBNN\n" +
            "8dM6PAYCJhl/f/AAbALtC5Qt8CPVAkAxbsxU3ufqBiIA1jpvF/56MLYrfDx1/icQ\n" +
            "Ub3pmwajEMvEfW5d2VP5uZCB34di+g2PqxroUYIRwvj4gwLGLyADAkEAgxLGiOJ8\n" +
            "T7hHvi1JOrYd1f7WQLyHiRRiBLEQllaXcDMUb70EJj0BHLVjY+rqqEFRiaAC5cBf\n" +
            "RQDR3PZp5mpCtw==\n";

    /**
     * 默认服务调用授权KEY名称
     */
    public static final String FEIGN_KEY = "_FeignKey_";

    /**
     * 默认服务之间调用TOKEN，用于验证服务调用方是否是授权调用方
     */
    public static final String DEFAULT_FEIGN_TOKEN = "wN4tJQJBAMamBKz3x9MmvclH2c3qpvsAsA3f8IKjKGAZsLFUC6i+OmMAyvwS";


}
