package sml;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static sml.Translator.getTranslatorInst;

/*
 * Spring DI configuration class
 */
@Configuration
@ComponentScan(basePackages = "sml")
public class SmlConfig {

    private static Translator translator;

    // get the instance of Translator
    public static void getTranslatorFromFile(String file) { translator = getTranslatorInst(file); }

    // Bean for Instruction class label parameter
    @Bean(value = "label")
    public String label() { return translator.getLabel(); }

    // Bean for Instruction class fileName parameter
    @Bean(value = "fileName")
    public String fileName() { return translator.getFileName();}

    // Bean for Instruction class opcode parameter
    @Bean(value = "opcode")
    public String opcode() { return translator.getOpcode();}

}
