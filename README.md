# ViaFabricPlus-3.4.9 Patch Mod (1.21.11 Support)

本專案是一個針對 `ViaFabricPlus-3.4.9` 的動態補丁 Mod，旨在將 `ViaFabricPlus-4.3.6` 中的 Minecraft 1.21.11 相關修復邏輯移植（Backport）到舊版本中。

## 核心修復目標

**「Fixed missing conversion of entities enchantment predicates in registry data」**

在 Minecraft 1.21.11 中，附魔（Enchantment）的資料結構發生了變化，引入了新的謂詞（Predicate）格式。舊版的 ViaFabricPlus/ViaVersion 無法正確處理這些嵌套在 Registry Data 中的謂詞，導致資料轉換遺失。

## 實作內容

本 Mod 使用 Fabric Mixin 技術，在類別載入時動態修改 ViaFabricPlus 的行為：

1.  **RegistryDataRewriter 攔截**：
    *   透過 Mixin 注入 `com.viaversion.viaversion.rewriter.RegistryDataRewriter.updateEnchantments`。
    *   取消原始的不完整轉換邏輯，轉而執行移植自 4.3.6 的增強型邏輯。

2.  **1.21.11 邏輯移植**：
    *   `EnchantmentLogic1_21_11`: 實作了遞迴掃描附魔效果（Effects）與謂詞（Requirements/Terms）的邏輯。
    *   `DataComponentPredicate`: 引入了 1.21.11 新版的數據組件謂詞資料結構及其網路傳輸序列化方式。

3.  **動態開關**：
    *   可透過 `com.example.ExampleMod.PATCH_ENABLED` 靜態變數動態啟用或關閉所有 Patch 邏輯。

## 專案結構

*   `src/main/java/com/example/mixin/`: 包含所有 Mixin 注入類別。
    *   `RegistryDataRewriterPatchMixin.java`: 核心注入點。
*   `src/main/java/com/example/logic/`: 移植自 ViaVersion 5.6.0 的核心處理邏輯。
*   `ViaFabricPlus-3.4.9.jar`: 目標修補檔案。
*   `viaversion-common-5.1.2-SNAPSHOT.jar`: 支援開發與編譯的依賴庫。

## 開發環境說明

*   **目標版本**: Minecraft 1.21.1
*   **Fabric Loader**: >= 0.19.2
*   **Java 版本**: 21
*   **編譯依賴**: 需要將 `ViaFabricPlus-3.4.9.jar` 與 `viaversion-common-5.1.2-SNAPSHOT.jar` 放置於根目錄以供 Gradle `compileOnly` 引用。

## 原始分析報告

原始的差異分析報告（4.3.5 -> 4.3.6）請參閱 `README_ORIGINAL.md`（原 README.md 內容已遷移）。
