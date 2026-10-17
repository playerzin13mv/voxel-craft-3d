package com.voxel.craft

data class Vector3(var x: Float, var y: Float, var z: Float) {
    fun add(v: Vector3) {
        x += v.x
        y += v.y
        z += v.z
    }
}

data class BlockHitResult(
    val blockX: Int,
    val blockY: Int,
    val blockZ: Int,
    val hitSide: Int // Identifica qual face do bloco foi atingida (para saber onde colocar o novo bloco)
)

object Raycaster {

    // Simula o raio partindo da câmera na direção que o jogador está olhando
    fun raycast(
        cameraPos: Vector3,
        cameraDir: Vector3,
        maxDistance: Float,
        getWorldBlock: (Int, Int, Int) -> Byte
    ): BlockHitResult? {
        val stepSize = 0.1f // Precisão do passo do raio
        var currentDistance = 0.0f
        
        val rayPos = Vector3(cameraPos.x, cameraPos.y, cameraPos.z)
        var lastX = -1
        var lastY = -1
        var lastZ = -1

        while (currentDistance < maxDistance) {
            val blockX = rayPos.x.toInt()
            val blockY = rayPos.y.toInt()
            val blockZ = rayPos.z.toInt()

            // Verifica se mudou de bloco para evitar checagens redundantes
            if (blockX != lastX || blockY != lastY || blockZ != lastZ) {
                val blockId = getWorldBlock(blockX, blockY, blockZ)
                if (blockId > 0) {
                    // Encontrou um bloco sólido! Retorna as coordenadas e aproxima a face
                    return BlockHitResult(blockX, blockY, blockZ, hitSide = 0)
                }
                lastX = blockX
                lastY = blockY
                lastZ = blockZ
            }

            rayPos.add(Vector3(cameraDir.x * stepSize, cameraDir.y * stepSize, cameraDir.z * stepSize))
            currentDistance += stepSize
        }

        return null // Nenhum bloco atingido no alcance
    }

    // Função para colocar um bloco adjacente à face atingida
    fun getPlacementPosition(hit: BlockHitResult, cameraDir: Vector3): Triple<Int, Int, Int> {
        // Dependendo de onde o raio bateu, decidimos o offset para posicionar o bloco ao lado
        // (Simplificação: inverte levemente a direção para achar o bloco vizinho livre)
        var offsetX = 0
        var offsetY = 0
        var offsetZ = 0

        if (cameraDir.x > 0) offsetX = -1 else if (cameraDir.x < 0) offsetX = 1
        if (cameraDir.y > 0) offsetY = -1 else if (cameraDir.y < 0) offsetY = 1
        if (cameraDir.z > 0) offsetZ = -1 else if (cameraDir.z < 0) offsetZ = 1

        return Triple(hit.blockX + offsetX, hit.blockY + offsetY, hit.blockZ + offsetZ)
    }
}
