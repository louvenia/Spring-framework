package edu.school21.app;

import edu.school21.preprocessor.PreProcessor;
import edu.school21.preprocessor.PreProcessorToUpperImpl;
import edu.school21.printer.Printer;
import edu.school21.printer.PrinterWithDateTimeImpl;
import edu.school21.renderer.Renderer;
import edu.school21.renderer.RendererStandardImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
    public static void main(String[] args) {
        System.out.println("-------------Standard way------------");
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererStandardImpl(preProcessor);
        PrinterWithDateTimeImpl printer = new PrinterWithDateTimeImpl(renderer);
        printer.print("Hello!");

        System.out.println("-------------Spring looks------------");
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Printer printerSpring = context.getBean("printPrefixErrLow", Printer.class);
        printerSpring.print("Hello");
    }
}