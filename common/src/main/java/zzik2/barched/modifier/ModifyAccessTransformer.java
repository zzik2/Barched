package zzik2.barched.modifier;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class ModifyAccessTransformer implements IMixinConfigPlugin {

    private static final int ACCESS_MASK = Opcodes.ACC_PUBLIC | Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED;

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        processFields(targetClass);
        processMethods(targetClass);
    }

    private void processFields(ClassNode classNode) {
        for (FieldNode field : classNode.fields) {
            ModifyAccessInfo info = getModifyAccessInfo(field.visibleAnnotations, field.invisibleAnnotations);
            if (info != null) {
                field.access = applyAccessModifier(field.access, info);
            }
        }
    }

    private void processMethods(ClassNode classNode) {
        for (MethodNode method : classNode.methods) {
            ModifyAccessInfo info = getModifyAccessInfo(method.visibleAnnotations, method.invisibleAnnotations);
            if (info != null) {
                method.access = applyAccessModifier(method.access, info);
            }
        }
    }
    private ModifyAccessInfo getModifyAccessInfo(List<?> visibleAnnotations, List<?> invisibleAnnotations) {
        String descriptor = "L" + ModifyAccess.class.getName().replace('.', '/') + ";";
        if (invisibleAnnotations != null) {
            for (Object annotation : invisibleAnnotations) {
                if (annotation instanceof org.objectweb.asm.tree.AnnotationNode node) {
                    if (descriptor.equals(node.desc)) {
                        return parseAnnotation(node);
                    }
                }
            }
        }
        return null;
    }

    private ModifyAccessInfo parseAnnotation(org.objectweb.asm.tree.AnnotationNode node) {
        int access = 0;
        boolean removeFinal = false;
        if (node.values != null) {
            for (int i = 0; i < node.values.size(); i += 2) {
                String name = (String) node.values.get(i);
                Object value = node.values.get(i + 1);
                if ("access".equals(name)) {
                    if (value instanceof List<?> list) {
                        for (Object item : list) {
                            if (item instanceof Integer intValue) {
                                access |= intValue;
                            }
                        }
                    } else if (value instanceof Integer intValue) {
                        access = intValue;
                    }
                } else if ("removeFinal".equals(name)) {
                    removeFinal = (Boolean) value;
                }
            }
        }
        return new ModifyAccessInfo(access, removeFinal);
    }

    private int applyAccessModifier(int original, ModifyAccessInfo info) {
        int newAccess = original & ~ACCESS_MASK;
        newAccess |= (info.access & ACCESS_MASK);
        if (info.removeFinal) {
            newAccess &= ~Opcodes.ACC_FINAL;
        } else if ((info.access & Opcodes.ACC_FINAL) != 0) {
            newAccess |= Opcodes.ACC_FINAL;
        }
        return newAccess;
    }

    private record ModifyAccessInfo(int access, boolean removeFinal) {
    }

}
