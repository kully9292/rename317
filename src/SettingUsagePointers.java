// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class SettingUsagePointers {

    public static void unpackConfig(JagexArchive jagexArchive)
    {
        Stream stream = new Stream(jagexArchive.getDataForName("varp.dat"));
        anInt702 = 0;
        int cacheSize = stream.readUnsignedWord();
        if(cache == null)
            cache = new SettingUsagePointers[cacheSize];
        if(anIntArray703 == null)
            anIntArray703 = new int[cacheSize];
        for(int j = 0; j < cacheSize; j++)
        {
            if(cache[j] == null)
                cache[j] = new SettingUsagePointers();
            cache[j].readValues(stream, j);
        }
        if(stream.currentOffset != stream.buffer.length)
            System.out.println("varptype load mismatch");
    }

    private void readValues(Stream stream, int i)
    {
        do
        {
            int j = stream.readUnsignedByte();
            if(j == 0)
                return;
            int dummy;
            if(j == 1)
                 stream.readUnsignedByte();
            else
            if(j == 2)
                stream.readUnsignedByte();
            else
            if(j == 3)
                anIntArray703[anInt702++] = i;
            else
            if(j == 4)
                dummy = 2;
            else
            if(j == 5)
                usage = stream.readUnsignedWord();
            else
            if(j == 6)
                dummy = 2;
            else
            if(j == 7)
                stream.readDWord();
            else
            if(j == 8)
                aBoolean713 = true;
             else
            if(j == 10)
                 stream.readString();
            else
            if(j == 11)
                aBoolean713 = true;
            else
            if(j == 12)
                stream.readDWord();
            else
            if(j == 13)
                dummy = 2;
            else
                System.out.println("Error unrecognised config code: " + j);
        } while(true);
    }

    private SettingUsagePointers()
    {
        aBoolean713 = false;
    }

    public static SettingUsagePointers cache[];
    private static int anInt702;
    private static int[] anIntArray703;
    public int usage;
    public boolean aBoolean713;

}