package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;

public class MethodParser {
    private final String OPEN_BRACKET = "\\(";
    private final String WHITE_SPACE = " ";
    private final String CLOSED_BRACKET = ")";
    private final String COMMA_AND_WHITE_SPACE = ", ";

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        String[] accessModifierReturnTypeMethodNameAndArguments = signatureString.split(OPEN_BRACKET);
        String[] accessModifierReturnTypeMethodName = accessModifierReturnTypeMethodNameAndArguments[0].split(WHITE_SPACE);

        MethodSignature methodSignature;
        boolean withAccessModifier = accessModifierReturnTypeMethodName.length == 3;
        String accessModifier = withAccessModifier ? accessModifierReturnTypeMethodName[0]
                : null;
        String returnType = withAccessModifier ? accessModifierReturnTypeMethodName[1]
                : accessModifierReturnTypeMethodName[0];
        String methodName = withAccessModifier ? accessModifierReturnTypeMethodName[2]
                : accessModifierReturnTypeMethodName[1];


        String argumentsString = accessModifierReturnTypeMethodNameAndArguments[1].replace(CLOSED_BRACKET, "");
        if (!argumentsString.isEmpty()) {
            String[] argumentsArray = argumentsString.split(COMMA_AND_WHITE_SPACE);
            List<MethodSignature.Argument> argumentList = new ArrayList<>();
            for (String argument : argumentsArray) {
                String[] argumentTypeAndName = argument.split(WHITE_SPACE);
                argumentList.add(new MethodSignature.Argument(argumentTypeAndName[0], argumentTypeAndName[1]));
            }
            methodSignature = new MethodSignature(methodName, argumentList);
        } else {
            methodSignature = new MethodSignature(methodName);
        }

        if (withAccessModifier) {
            methodSignature.setAccessModifier(accessModifier);
        }

        methodSignature.setReturnType(returnType);
        return methodSignature;
    }
}
