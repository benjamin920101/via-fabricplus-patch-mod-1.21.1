package com.example.logic;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.Tag;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Extracted logic for 1.21.11 Enchantment processing.
 * Based on analysis of ViaVersion 5.6.0 RegistryDataRewriter.
 */
public class EnchantmentLogic1_21_11 {

    public static void updateEnchantment(CompoundTag enchantmentTag, Map<String, Consumer<CompoundTag>> effectHandlers) {
        // Update supported_items and primary_items if needed (item mapping logic)
        
        CompoundTag effects = enchantmentTag.getCompoundTag("effects");
        if (effects == null) return;

        for (Map.Entry<String, Tag> entry : effects.entrySet()) {
            Tag value = entry.getValue();
            if (value instanceof CompoundTag) {
                updateNestedEffect((CompoundTag) value, effectHandlers);
            } else if (value instanceof ListTag) {
                ListTag list = (ListTag) value;
                if (list.getElementType() == CompoundTag.class) {
                    for (Tag element : list) {
                        updateNestedEffect((CompoundTag) element, effectHandlers);
                    }
                }
            }
        }
    }

    private static void updateNestedEffect(CompoundTag effectTag, Map<String, Consumer<CompoundTag>> effectHandlers) {
        CompoundTag effect = effectTag.getCompoundTag("effect");
        if (effect != null) {
            runEffectRewriters(effect, effectHandlers);
            
            // Recursive effects - In 5.1.2, getListTag might have different signature or we use get
            ListTag nestedEffects = effect.getListTag("effects", CompoundTag.class);
            if (nestedEffects != null) {
                for (Tag nested : nestedEffects) {
                    runEffectRewriters((CompoundTag) nested, effectHandlers);
                }
            }
        }

        // Handle requirements (Entity Predicates) - 1.21.11 fix
        CompoundTag requirements = effectTag.getCompoundTag("requirements");
        if (requirements != null) {
            processEntityPredicate(requirements);
        }
        
        // Handle terms in requirements (Missing in 3.4.9/5.1.2)
        if (requirements != null) {
            ListTag terms = requirements.getListTag("terms", CompoundTag.class);
            if (terms != null) {
                for (Tag term : terms) {
                    processEntityPredicate((CompoundTag) term);
                }
            }
        }
    }

    private static void runEffectRewriters(CompoundTag effect, Map<String, Consumer<CompoundTag>> effectHandlers) {
        String type = effect.getString("type");
        Consumer<CompoundTag> handler = effectHandlers.get(type);
        if (handler != null) {
            handler.accept(effect);
        }
    }

    private static void processEntityPredicate(CompoundTag predicate) {
        // 1.21.11 specific logic for enchantment predicates in entity data
        // This would involve converting DataComponentPredicate formats
        // For demonstration, we log the processing
        System.out.println("Processing 1.21.11 Entity Predicate: " + predicate.entrySet());
    }
}
