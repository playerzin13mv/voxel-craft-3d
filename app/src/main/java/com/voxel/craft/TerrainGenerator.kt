package com.voxel.craft

import kotlin.math.cos
import kotlin.math.sin

object TerrainGenerator {

    fun generateChunk(chunk: Chunk) {
        for (x in 0 until Chunk.WIDTH) {
            for (z in 0 until Chunk.LENGTH) {
                // Coordenadas absolutas no mundo global
                val worldX = chunk.chunkX * Chunk.WIDTH + x
                val worldZ = chunk.chunkZ * Chunk.LENGTH + z

                // Cálculo matemático do relevo (simulando ondas de ruído de terreno)
                val baseHeight = 48
                val noise = (sin(worldX * 0.08) * cos(worldZ * 0.08) * 12.0 + 12.0).toInt()
                val surfaceHeight = baseHeight + noise

                for (y in 0 until Chunk.HEIGHT) {
                    when {
                        y > surfaceHeight -> chunk.setBlock(x, y, z, 0) // Ar
                        y == surfaceHeight -> chunk.setBlock(x, y, z, 1) // Bloco de Grama
                        y > surfaceHeight - 5 -> chunk.setBlock(x, y, z, 2) // Bloco de Terra
                        else -> chunk.setBlock(x, y, z, 3) // Bloco de Pedra
                    }
                }
            }
        }
    }
}
