package zzik2.barched.mixin;

import zzik2.zreflex.mixin.ModifyAccessTransformer;

public final class BarchedMixinPlugin extends ModifyAccessTransformer {

    private static final String COMPAT_MIXIN_PACKAGE = BarchedMixinPlugin.class.getPackageName() + ".compat.";

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return super.shouldApplyMixin(targetClassName, mixinClassName) && (!mixinClassName.startsWith(COMPAT_MIXIN_PACKAGE) || isClassPresent(targetClassName));
    }

    private static boolean isClassPresent(String className) {
        String resourceName = className.replace('.', '/') + ".class";
        return BarchedMixinPlugin.class.getClassLoader().getResource(resourceName) != null;
    }
}
