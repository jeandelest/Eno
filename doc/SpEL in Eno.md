# Usage of Spring Expression Language in Eno

This page provides documentation of the parameters that can be used in annotations on the Eno model
(these parameters are hardcoded in mappers).

The second part is a tutorial of the SpEL syntax and corresponding functionalities 
that are used in the different annotations. 
This tutorial provides a comprehensive guide for the contributor 
to the project.

## Eno parameter keywords

- ``param``: used in out mapper to set property values
- ``index``: used in DDI mapper to reference the map that contains DDI objects
- ``lunaticIndex``: used in Lunatic mapper to reference the map that contains components

## SpEL functionalities / syntax

### SpEL keywords

- ``this``
- ``root``

### Logical operators

- ``==``
- ``!=``
- ``instanceof``

### Projection

``.!``

### Selection

``.?``

### Types

``T()``

### Enums

``T(fully.qualified.name.SomeEnum).SOME_VALUE``
``T(fully.qualified.name.SomeEnum).valueOf('SOME_VALUE')``

### Inheritance

````java
public class Instruction {
    
    @DDI(contextType = InstructionType.class,
            field = "getInstructionTextArray(0).getTextContentArray(0).getText().getStringValue()")
    String label;
    
}
````

Here, `getTextContentArray(0)` returns a `TextContentType` object. 
The `TextContentType` class does not have a `getText()` method.
Yet, in this context, we know that the object we will have at runtime 
is a `LiteralTextType` (which is a superclass of `TextContentType`) that has this method.
