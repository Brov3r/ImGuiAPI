package io.github.brov3r.imgui.patches;

import com.avrix.agent.ClassTransformer;
import io.github.brov3r.imgui.ImGuiManager;
import javassist.CannotCompileException;

/**
 * SpriteRenderer patcher
 */
public class SpriteRendererPatch extends ClassTransformer {
    /**
     * Constructor for creating a {@link ClassTransformer} object.
     */
    public SpriteRendererPatch() {
        super("zombie.core.SpriteRenderer");
    }

    /**
     * Method for performing class modification.
     * The implementing method must contain the logic for modifying the target class.
     */
    @Override
    public void modifyClass() {
        getModifierBuilder().modifyMethod("postRender", (ctClass, ctMethod) -> {
            try {
                ctMethod.insertAfter(ImGuiManager.class.getName() + ".render();");
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
