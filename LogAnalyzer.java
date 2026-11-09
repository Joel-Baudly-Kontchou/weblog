
/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version 7.0
 */
public class LogAnalyzer
{
    public static final int HOURS_PER_DAY = 24;
    
    private int numberOfAccesses;
    
    private int busiestHour;
    
    private int quietestHour; 
    
    private int busiestTwoHour;
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String filename)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[HOURS_PER_DAY];
        // Create the reader to obtain the data.
        reader = new LogfileReader(filename);
    }
    
    public int numberOfAccesses(){
    int total = 0;
    for(int hour = 0; hour < hourCounts.length; hour++) {
        total = total + hourCounts[hour];
        // or shorter: total += hourCounts[hour];
    }
    return total;
    } 
    
    public int busiestHour(){
    int busiest = 0;
    for(int hour = 1; hour < hourCounts.length; hour++) {
        if(hourCounts[hour] > hourCounts[busiest]) {
            busiest = hour;
        }
    }
    return busiest;
    } 
    
    public int quietestHour(){
    int quietest = 0; 
    for(int hour = 1; hour < hourCounts.length; hour++) {
        if(hourCounts[hour] < hourCounts[quietest]) {
            quietest = hour;
        }
    }
    return quietest;
    }
    
    public int busiestTwoHour(){
    int busiestHour = 0;
    int maxCount = hourCounts[0] + hourCounts[1];
    for(int hour = 1; hour < hourCounts.length - 1; hour++) {
        int currentCount = hourCounts[hour] + hourCounts[hour + 1];
        if(currentCount > maxCount) {
            maxCount = currentCount;
            busiestHour = hour;
        }
    }
    return busiestHour;
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        int hour = 0;
        while( hour < hourCounts.length) {
            System.out.println(hour + ": " + hourCounts[hour]);
            hour++; 
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
