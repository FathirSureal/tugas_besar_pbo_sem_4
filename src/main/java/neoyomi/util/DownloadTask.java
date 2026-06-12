/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neoyomi.util;

/**
 *
 * @author Lenov
 */
public abstract class DownloadTask {
    protected int progress = 0;
    
    public abstract void startDownload(); 
    
    public int getProgress() {
        return progress;
    }
}