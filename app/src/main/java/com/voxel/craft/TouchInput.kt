package com.voxel.craft

class TouchInput {
    var selectedHotbarSlot: Int = 0
    var isBreakingBlock: Boolean = false
    var isPlacingBlock: Boolean = false

    // Simula o toque na tela de toque do Android (MotionEvent)
    fun handleTouch(x: Float, y: Float, screenWidth: Int, screenHeight: Int): String {
        // Verifica se o toque foi na região da Hotbar (parte inferior da tela)
        if (y > screenHeight - 120) {
            val slotWidth = screenWidth / 9
            selectedHotbarSlot = (x / slotWidth).toInt().coerceIn(0, 8)
            return "Selecionado Slot da Hotbar: ${selectedHotbarSlot + 1}"
        }

        // Botão virtual direito (Ex: Quebrar/Colocar no canto inferior direito)
        if (x > screenWidth - 150 && y > screenHeight - 300 && y < screenHeight - 150) {
            isBreakingBlock = true
            return "Ação: Quebrar Bloco"
        }

        if (x > screenWidth - 150 && y > screenHeight - 450 && y < screenHeight - 300) {
            isPlacingBlock = true
            return "Ação: Colocar Bloco"
        }

        return "Movendo Câmera / Olhando ao redor"
    }

    fun resetActions() {
        isBreakingBlock = false
        isPlacingBlock = false
    }
}
