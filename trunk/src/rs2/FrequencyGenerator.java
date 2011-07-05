package rs2;

public class FrequencyGenerator//mhm not too sure
{

    private float method541(int i, int j, float f)
    {
        float f1 = (float)anIntArrayArrayArray667[i][0][j] + f * (float)(anIntArrayArrayArray667[i][1][j] - anIntArrayArrayArray667[i][0][j]);
            f1 *= 0.001525879F;
            return 1.0F - (float)Math.pow(10D, -f1 / 20F);
    }

    private float calculateDigitalFrequency(float note)// like angular velocity or some shit
    {//32.7032f Represents the note C1 on a piano keyboard. 
        float frequency = 32.7032F * (float)Math.pow(2D, note);
        return (frequency * 3.141593F) / 11025F;
    }

    private float method543(float f, int i, int j)
    {
        float f1 = (float)anIntArrayArrayArray666[j][0][i] + f * (float)(anIntArrayArrayArray666[j][1][i] - anIntArrayArrayArray666[j][0][i]);
        f1 *= 0.0001220703F;
        return calculateDigitalFrequency(f1);
    }

    public int method544(int i, float f)
    {
        if(i == 0)
        {
            float f1 = (float)anIntArray668[0] + (float)(anIntArray668[1] - anIntArray668[0]) * f;
            f1 *= 0.003051758F;// some kind of transformer?
            aFloat671 = (float)Math.pow(0.10000000000000001D, f1 / 20F);
            anInt672 = (int)(aFloat671 * 65536F);
        }
        if(anIntArray665[i] == 0)
            return 0;
        float f2 = method541(i, 0, f);
        aFloatArrayArray669[i][0] = -2F * f2 * (float)Math.cos(method543(f, 0, i));
        aFloatArrayArray669[i][1] = f2 * f2;
        for(int k = 1; k < anIntArray665[i]; k++)
        {
            float f3 = method541(i, k, f);
            float f4 = -2F * f3 * (float)Math.cos(method543(f, k, i));
            float f5 = f3 * f3;
            aFloatArrayArray669[i][k * 2 + 1] = aFloatArrayArray669[i][k * 2 - 1] * f5;
            aFloatArrayArray669[i][k * 2] = aFloatArrayArray669[i][k * 2 - 1] * f4 + aFloatArrayArray669[i][k * 2 - 2] * f5;
            for(int j1 = k * 2 - 1; j1 >= 2; j1--)
                aFloatArrayArray669[i][j1] += aFloatArrayArray669[i][j1 - 1] * f4 + aFloatArrayArray669[i][j1 - 2] * f5;

            aFloatArrayArray669[i][1] += aFloatArrayArray669[i][0] * f4 + f5;
            aFloatArrayArray669[i][0] += f4;
        }

        if(i == 0)
        {
            for(int l = 0; l < anIntArray665[0] * 2; l++)
                aFloatArrayArray669[0][l] *= aFloat671;

        }
        for(int i1 = 0; i1 < anIntArray665[i] * 2; i1++)
            anIntArrayArray670[i][i1] = (int)(aFloatArrayArray669[i][i1] * 65536F);

        return anIntArray665[i] * 2;
    }

    public void method545(Packet stream, AmplitudeEnvelope amplitudeEnvelope)
    {
        int i = stream.g1();
        anIntArray665[0] = i >> 4;
        anIntArray665[1] = i & 0xf;
        if(i != 0)
        {
            anIntArray668[0] = stream.g2();
            anIntArray668[1] = stream.g2();
            int j = stream.g1();
            for(int k = 0; k < 2; k++)
            {
                for(int l = 0; l < anIntArray665[k]; l++)
                {
                    anIntArrayArrayArray666[k][0][l] = stream.g2();
                    anIntArrayArrayArray667[k][0][l] = stream.g2();
                }

            }

            for(int i1 = 0; i1 < 2; i1++)
            {
                for(int j1 = 0; j1 < anIntArray665[i1]; j1++)
                    if((j & 1 << i1 * 4 << j1) != 0)
                    {
                        anIntArrayArrayArray666[i1][1][j1] = stream.g2();
                        anIntArrayArrayArray667[i1][1][j1] = stream.g2();
                    } else
                    {
                        anIntArrayArrayArray666[i1][1][j1] = anIntArrayArrayArray666[i1][0][j1];
                        anIntArrayArrayArray667[i1][1][j1] = anIntArrayArrayArray667[i1][0][j1];
                    }

            }

            if(j != 0 || anIntArray668[1] != anIntArray668[0])
                amplitudeEnvelope.readValues(stream);
        } else
        {
            anIntArray668[0] = anIntArray668[1] = 0;
        }
        if(1 != 1)
        for(int bbbb = 0; bbbb < anIntArrayArrayArray666.length;bbbb++)
        	for(int b = 0; b < anIntArrayArrayArray666[bbbb].length;b++)
        		for(int c = 0; c < anIntArrayArrayArray666[bbbb][b].length;c++)
        		{
        			System.out.println("anIntArrayArrayArray666["+bbbb+"]["+b+"]["+c+"] = "+ anIntArrayArrayArray666[bbbb][b][c]);
        		}
    }

    public FrequencyGenerator()
    {
        anIntArray665 = new int[2];
        anIntArrayArrayArray666 = new int[2][2][4];
        anIntArrayArrayArray667 = new int[2][2][4];
        anIntArray668 = new int[2];
    }

    final int[] anIntArray665;
    private final int[][][] anIntArrayArrayArray666;
    private final int[][][] anIntArrayArrayArray667;
    private final int[] anIntArray668;
    private static final float[][] aFloatArrayArray669 = new float[2][8];
    static final int[][] anIntArrayArray670 = new int[2][8];
    private static float aFloat671;
    static int anInt672;

}
