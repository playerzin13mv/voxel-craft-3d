package com.voxel.craft

class Chunk(val chunkX: Int, val chunkZ: Int) {
    companion object {
        const val WIDTH = 16
        const val HEIGHT = 128
        const val LENGTH = 16
    }

    // Matriz 3D que armazena o ID de cada bloco no chunk
    private val blocks = Array(WIDTH) { Array(HEIGHT) { ByteArray(LENGTH) } }

    fun getBlock(x: Int, y: Int, z: Int): Byte {
        if (x in 0 until WIDTH && y in 0 until HEIGHT && z in 0 until LENGTH) {
            blocks[x][y][z]
        }
        return 0 // Retorna Ar se estiver fora dos limites
    }

    fun setBlock(x: Int, y: Int, z: Int, type: Byte) {
        if (x in 0 until WIDTH && y in 0 until HEIGHT && z in 0 until LENGTH) {
            blocks[x][y][z] = type
        }
    }
}
