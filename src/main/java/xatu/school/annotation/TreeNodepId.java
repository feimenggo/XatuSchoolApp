package xatu.school.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)  //表明注解声明在哪些地方（类，属性，方法），，属性上面
@Retention(RetentionPolicy.RUNTIME)  //表示什么时候可见，，此处为运行时可见
public @interface TreeNodepId
{
    
}
