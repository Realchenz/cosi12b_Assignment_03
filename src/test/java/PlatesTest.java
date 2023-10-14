import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.Field;

public class PlatesTest {
    @Test
    public void testCreateRandomPlate() {
        String serial = "123AB4";
        int month = 5;
        String plate = Plates.createRandomPlate(serial, month);
        System.out.println(plate);
    }

    @Test
    public void testNextPlate() {
        String plate = "123AB9";
        String nextPlate = Plates.nextPlate(plate);
        System.out.println(nextPlate);
    }

    @Test
    public void testGetSerial() {
        String plate = "12SD46";
        String serial = Plates.getSerial(plate);
        System.out.println(serial);
        Assert.assertEquals("12AB34",serial);
    }

    @Test
    public void testIsLegalVanityPlate() {
        String plate = "AAA22A";
        boolean isLegal = Plates.isLegalVanityPlate(plate);
        System.out.println(isLegal);
    }

/*    @Test
    public void testReadPlatesFromFile() {
        String fileName = "plates.txt";
        String[] plates = Plates.readPlatesFromFile(fileName);
        for (String plate : plates) {
            System.out.println(plate);
        }
}*/

    @Test
    public void testGetMonthStats() throws NoSuchFieldException, IllegalAccessException {
        Plates instancePlates = new Plates();
        Field field = Plates.class.getDeclaredField("plates");
        field.setAccessible(true);
        String[] plates = (String[]) field.get(instancePlates);
        float[] monthStats = Plates.getMonthStats(plates);
        for (int i = 0; i < monthStats.length; i++) {
            System.out.println(i + ": " + monthStats[i]);
        }
    }

    @Test
    public void testGetSerialStats() throws NoSuchFieldException, IllegalAccessException {
        Plates instancePlates = new Plates();
        Field field = Plates.class.getDeclaredField("plates");
        field.setAccessible(true);
        String[] plates = (String[]) field.get(instancePlates);
        float[] serialStats = Plates.getSerialStats(plates, new String[]{"12AB34", "123AB4", "1AB234", "1ABC23"});
        for (int i = 0; i < serialStats.length; i++) {
            System.out.println(i + ": " + serialStats[i]);
        }
    }

    @Test
    public void testMatchPlate() throws NoSuchFieldException, IllegalAccessException {
        Plates instancePlates = new Plates();
        Field field = Plates.class.getDeclaredField("plates");
        field.setAccessible(true);
        String[] plates = (String[]) field.get(instancePlates);
        String partialPlate = "51-13";
        String[] matches = Plates.matchPlate(partialPlate, plates);
        for (String match : matches) {
            System.out.println(match);
        }
    }

}

