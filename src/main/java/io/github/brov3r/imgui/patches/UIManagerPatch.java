package io.github.brov3r.imgui.patches;

import com.avrix.agent.ClassTransformer;
import io.github.brov3r.imgui.ImGuiManager;
import javassist.CannotCompileException;

/**
 * UIManager patcher
 */
public class UIManagerPatch extends ClassTransformer {
    /**
     * Constructor for creating a {@link ClassTransformer} object.
     */
    public UIManagerPatch() {
        super("zombie.ui.UIManager");
    }

    /**
     * Method for performing class modification.
     * The implementing method must contain the logic for modifying the target class.
     */
    @Override
    public void modifyClass() {
        getModifierBuilder().modifyMethod("update", (ctClass, ctMethod) -> {
            try {
                ctMethod.insertBefore("{ if (" + ImGuiManager.class.getName() + ".isMouseCapture()) return; }");
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
