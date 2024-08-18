package io.github.brov3r.imgui.patches;

import com.avrix.agent.ClassTransformer;
import io.github.brov3r.imgui.ImGuiManager;
import javassist.CannotCompileException;

/**
 * Game window patcher
 */
public class GameWindowPatch extends ClassTransformer {
    /**
     * Constructor for creating a {@link ClassTransformer} object.
     */
    public GameWindowPatch() {
        super("zombie.GameWindow");
    }

    /**
     * Method for performing class modification.
     * The implementing method must contain the logic for modifying the target class.
     */
    @Override
    public void modifyClass() {
        getModifierBuilder().modifyMethod("InitDisplay", (ctClass, ctMethod) -> {
            try {
                ctMethod.insertAfter(ImGuiManager.class.getName() + ".init();");
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });
    }
}