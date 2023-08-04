package cool.zgq.component.mp.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.Scanner;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 08/01 20:14
 * @Description
 */
public class CodeGenerator {
    /**
     * 父项目地址
     */
    public static String PROJECT_PATH = System.getProperty("user.dir");

    public static String SUB_PATH = "";
    /**
     * 包路径
     */
    public static String PACKAGE_PATH = "cool.zgq.domain";
    /**
     * 包路径
     */
    public static String MODULE_NAME;
    /**
     * 实体类父类
     */
    public static String SUPER_ENTITY_CLASS = "cool.zgq.component.common.entity.BaseEntity";
    /**
     * 作者
     */
    public static String AUTHOR = "GQ";
    /**
     * 数据库DRIVER_NAME
     */
    public static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    /**
     * 数据库URL
     */
    public static String DRIVER_URL = "127.0.0.1:3306";

    /**
     * 数据库URL
     */
    public static String DATABASE_NAME;

    /**
     * 数据库完整路径  如果有值 优先于 DRIVER_URL
     */
    public static String DRIVER_FULL_PATH = null;
    /**
     * 数据库账号
     */
    public static String USERNAME = "root";
    /**
     * 数据库密码
     */
    public static String PASSWORD = "123456";

    /**
     * 要生成的表 如果不传入 则为所有表
     */
    private static String[] GENERATOR_TABLES = {};


    private static String[] TABLE_PREFIX = {};


    private static MySqlTypeConvert CONVERT = new DefaultConvert();

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入域服务" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            //TODO
//            if (StringUtils.isNotBlank(ipt)) {
//                return ipt;
//            }
            return ipt;
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void setProperties(GeneratorProperties properties) {

        if (StringUtils.isNotBlank(properties.getProjectPath())) {
            PROJECT_PATH = properties.getProjectPath();
        }

        if (StringUtils.isNotBlank(properties.getSubPath())) {
            SUB_PATH = properties.getSubPath();
        }

        if (StringUtils.isNotBlank(properties.getPackagePath())) {
            PACKAGE_PATH = properties.getPackagePath();
        }

        if (StringUtils.isNotBlank(properties.getDriverUrl())) {
            DRIVER_URL = properties.getDriverUrl();
        }

        if (StringUtils.isNotBlank(properties.getDataBaseName())) {
            DATABASE_NAME = properties.getDataBaseName();
        }

        if (StringUtils.isNotBlank(properties.getDriverFullPath())) {
            DRIVER_FULL_PATH = properties.getDriverFullPath();
        }

        if (StringUtils.isNotBlank(properties.getUsername())) {
            USERNAME = properties.getUsername();
        }

        if (StringUtils.isNotBlank(properties.getPassword())) {
            PASSWORD = properties.getPassword();
        }

        if (StringUtils.isNotBlank(properties.getDriverName())) {
            DRIVER_NAME = properties.getDriverName();
        }

        if (StringUtils.isNotBlank(properties.getAuthor())) {
            AUTHOR = properties.getAuthor();
        }

        if (properties.getConvert() != null) {
            CONVERT = properties.getConvert();
        }


        if (StringUtils.isNotBlank(properties.getSuperClass())) {
            SUPER_ENTITY_CLASS = properties.getSuperClass();
        }


        if (StringUtils.isNotBlank(properties.getModuleName())) {
            MODULE_NAME = properties.getModuleName();
        }

        if (!ObjectUtils.isEmpty(properties.getTables())) {
            GENERATOR_TABLES = properties.getTables();
        }

        if (!ObjectUtils.isEmpty(properties.getTablePreFix())) {
            TABLE_PREFIX = properties.getTablePreFix();
        }
    }


    private static void atuoGenerator(DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, GlobalConfig globalConfig, PackageConfig packageConfig, InjectionConfig injectionConfig) {
        // 代码生成器
        AutoGenerator autoGenerator = new MyGenerator(dataSourceConfig);
        autoGenerator.global(globalConfig)
                .packageInfo(packageConfig)
                .strategy(strategyConfig)
                .template(new TemplateConfig.Builder().controller(null).build())
                .injection(injectionConfig)
                // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
                .execute(new FreemarkerTemplateEngine());

    }

    private static GlobalConfig getGlobalConfig() {
        GlobalConfig.Builder builder = new GlobalConfig.Builder();
        builder.outputDir(PROJECT_PATH + SUB_PATH + "/src/main/java")
                .author(AUTHOR)
//                .disableOpenDir()
                .enableSwagger()
                .fileOverride();
        return builder.build();
    }

    private static DataSourceConfig getDataSourceConfig() {
        return new DataSourceConfig.
                Builder(driverProjectName(), USERNAME, PASSWORD)
                .schema(DATABASE_NAME)
                .typeConvert(CONVERT)
                .build();
    }

    private static PackageConfig getPackageConfig() {
        PackageConfig.Builder builder = new PackageConfig.Builder();
        String moduleName = "";
        if (StringUtils.isNotBlank(MODULE_NAME)) {
            moduleName = MODULE_NAME + ".orm";
        } else {
            moduleName = "orm";
        }

        builder.parent(PACKAGE_PATH).moduleName(moduleName);
        return builder.build();
    }

    private static StrategyConfig getStrategyConfig() {
        StrategyConfig.Builder builder = new StrategyConfig.Builder();


        builder.entityBuilder()
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .superClass(SUPER_ENTITY_CLASS)
                .enableLombok()

                .idType(IdType.ASSIGN_ID)
                .addSuperEntityColumns("id", "deleted", "create_time", "update_time", "create_by", "update_by", "version")
                .logicDeleteColumnName("deleted").fileOverride();

        if (!ObjectUtils.isEmpty(TABLE_PREFIX)) {
            builder.addTablePrefix(TABLE_PREFIX);
        }


        if (!ObjectUtils.isEmpty(GENERATOR_TABLES)) {
            builder.addInclude(GENERATOR_TABLES);
        } else {
            builder.addExclude("undo_log");
        }

        return builder.build();
    }

    private static InjectionConfig getInjectionConfig() {
        // 自定义配置
        InjectionConfig.Builder builder = new InjectionConfig.Builder().fileOverride();

        return builder.build();
    }


    private static String driverProjectName() {

        if (StringUtils.isNotBlank(DRIVER_FULL_PATH)) {
            return DRIVER_FULL_PATH;
        } else {
            return "jdbc:mysql://" + DRIVER_URL + "/" + DATABASE_NAME + "?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
        }
    }

    /**
     * 生成代码
     */
    public static void generator(GeneratorProperties properties) {

        setProperties(properties);
        // 全局配置
        GlobalConfig globalConfig = getGlobalConfig();
        // 数据源配置
        DataSourceConfig dataSourceConfig = getDataSourceConfig();

        // 包名配置
        PackageConfig packageConfig = getPackageConfig();

        // 策略配置
        StrategyConfig strategyConfig = getStrategyConfig();

        // 自定义配置
        InjectionConfig injectionConfig = getInjectionConfig();

        //自动生成
        atuoGenerator(dataSourceConfig, strategyConfig, globalConfig, packageConfig, injectionConfig);

    }
}