package net.minecraft.src;

public class IntArray
{
    /**
     * Int array of data stored in this holder. Possibly a light map or some chunk data.
     */
    public final int[] data;

    /**
     * Log base 2 of the chunk height (128); applied as a shift on Z coordinate
     */
    private final int depthBits;

    /**
     * Log base 2 of the chunk height (128) * width (16); applied as a shift on X coordinate
     */
    private final int depthBitsPlusFour;

    public IntArray(int size, int height)
    {
        this.data = new int[size];
        this.depthBits = height;
        this.depthBitsPlusFour = height + 4;
    }

    public IntArray(int[] newArray, int height)
    {
        this.data = newArray;
        this.depthBits = height;
        this.depthBitsPlusFour = height + 4;
    }

    /**
     * Returns the nibble of data corresponding to the passed in x, y, z. y is at most 6 bits, z is at most 4.
     */
    public int get(int x, int y, int z)
    {
        int index = y << this.depthBitsPlusFour | z << this.depthBits | x;
        return this.data[index];
    }

    /**
     * Arguments are x, y, z, val. Sets the nibble of data at x << 11 | z << 7 | y to val.
     */
    public void set(int x, int y, int z, int newValue)
    {
        int index = y << this.depthBitsPlusFour | z << this.depthBits | x;
        this.data[index] = newValue;
    }
}
