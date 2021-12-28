package interbase.interclient;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;

public final class IBTimestamp {
   int encodedYearMonthDay_ = 0;
   int encodedHourMinuteSecond_ = 0;
   private int year_;
   private int month_;
   private int date_;
   private int hour_;
   private int minute_;
   private int second_;
   private int millisByTen_ = 0;
   private int nanos_ = 0;
   private static final int PRECISION__ = 10000;
   private static final int TIME_ZONE__ = 0;
   static final int DATE = 0;
   static final int TIME = 1;
   static final int DATETIME = 2;
   private InnerCalendar cal = new InnerCalendar();

   public long getTimeInMillis() {
      this.cal.set(1, this.year_ + 1900);
      this.cal.set(2, this.month_);
      this.cal.set(5, this.date_);
      this.cal.set(11, this.hour_);
      this.cal.set(12, this.minute_);
      this.cal.set(13, this.second_);
      this.cal.set(14, this.millisByTen_ / 10);
      return this.cal.getTheTimeInMillis();
   }

   public long getJustTime() {
      this.cal = new InnerCalendar(1970, 0, 1);
      this.cal.set(11, this.hour_);
      this.cal.set(12, this.minute_);
      this.cal.set(13, this.second_);
      this.cal.set(14, this.millisByTen_ / 10);
      return this.cal.getTheTimeInMillis();
   }

   public long getJustDate() {
      this.cal = new InnerCalendar(this.year_ + 1900, this.month_, this.date_);
      return this.cal.getTheTimeInMillis();
   }

   public IBTimestamp() {
   }

   IBTimestamp(Date var1) {
      this.cal.setTime(var1);
      this.year_ = this.cal.get(1) - 1900;
      this.month_ = this.cal.get(2);
      this.date_ = this.cal.get(5);
      this.hour_ = this.cal.get(11);
      this.minute_ = this.cal.get(12);
      this.second_ = this.cal.get(13);
      if (var1 instanceof Timestamp) {
         this.nanos_ = ((Timestamp)var1).getNanos();
         this.millisByTen_ = this.nanos_ / 100000;
      } else {
         this.millisByTen_ = this.cal.get(14) * 10;
      }

      this.encodeYearMonthDay();
      this.encodeHourMinuteSecond();
   }

   IBTimestamp(int var1, int[] var2) throws interbase.interclient.BugCheckException {
      this.setTimestampId(var1, var2);
   }

   IBTimestamp(int var1, int var2) {
      this.setDateTime(var1, var2);
   }

   void setDateTime(int var1, int var2) {
      switch(var1) {
      case 0:
         this.encodedYearMonthDay_ = var2;
         this.decodeYearMonthDay();
         break;
      case 1:
         this.encodedHourMinuteSecond_ = var2;
         this.decodeHourMinuteSecond();
      }

   }

   void setTimestampId(int var1, int[] var2) throws interbase.interclient.BugCheckException {
      switch(var1) {
      case 0:
         this.encodedYearMonthDay_ = var2[0];
         this.decodeYearMonthDay();
         break;
      case 1:
         this.encodedHourMinuteSecond_ = var2[1];
         this.decodeHourMinuteSecond();
         break;
      case 2:
         this.encodedYearMonthDay_ = var2[0];
         this.encodedHourMinuteSecond_ = var2[1];
         this.decodeYearMonthDay();
         this.decodeHourMinuteSecond();
         break;
      default:
         throw new BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 116);
      }

   }

   IBTimestamp(int var1, int var2, int var3) {
      this.year_ = var1;
      this.month_ = var2;
      this.date_ = var3;
      this.encodeYearMonthDay();
   }

   IBTimestamp(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      this.year_ = var1;
      this.month_ = var2;
      this.date_ = var3;
      this.hour_ = var4;
      this.minute_ = var5;
      this.second_ = var6;
      this.millisByTen_ = var7 / 100000;
      this.encodeYearMonthDay();
      this.encodeHourMinuteSecond();
   }

   int getYear() {
      return this.year_;
   }

   int getMonth() {
      return this.month_;
   }

   int getDate() {
      return this.date_;
   }

   int getHours() {
      return this.hour_;
   }

   int getMinutes() {
      return this.minute_;
   }

   int getSeconds() {
      return this.second_;
   }

   int getNanos() {
      return this.millisByTen_ * 100000;
   }

   private void encodeYearMonthDay() {
      int var3 = this.year_ + 1900;
      int var4 = this.month_ + 1;
      if (var4 > 2) {
         var4 -= 3;
      } else {
         var4 += 9;
         --var3;
      }

      int var1 = var3 / 100;
      int var2 = var3 - 100 * var1;
      this.encodedYearMonthDay_ = 146097 * var1 / 4 + 1461 * var2 / 4 + (153 * var4 + 2) / 5 + this.date_ + 1721119 - 2400001;
   }

   private void encodeHourMinuteSecond() {
      int var1 = this.hour_ * 60 + this.minute_ - 0;
      this.encodedHourMinuteSecond_ = var1 * 60 * 10000 + this.second_ * 10000 + this.millisByTen_;
   }

   private void decodeYearMonthDay() {
      long var1 = (long)this.encodedYearMonthDay_;
      var1 -= -678882L;
      this.year_ = (int)((4L * var1 - 1L) / 146097L);
      var1 = (long)((int)(4L * var1 - 1L - (long)(146097 * this.year_)));
      this.date_ = (int)(var1 / 4L);
      var1 = (long)((4 * this.date_ + 3) / 1461);
      this.date_ = (int)((long)(4 * this.date_ + 3) - 1461L * var1);
      this.date_ = (this.date_ + 4) / 4;
      this.month_ = (5 * this.date_ - 3) / 153;
      this.date_ = 5 * this.date_ - 3 - 153 * this.month_;
      this.date_ = (this.date_ + 5) / 5;
      this.year_ = (int)((long)(100 * this.year_) + var1);
      if (this.month_ < 10) {
         this.month_ += 3;
      } else {
         this.month_ -= 9;
         ++this.year_;
      }

      this.year_ -= 1900;
      --this.month_;
   }

   private void decodeHourMinuteSecond() {
      int var1 = this.encodedHourMinuteSecond_ / 600000 + 0;
      this.hour_ = var1 / 60;
      this.minute_ = var1 % 60;
      this.second_ = this.encodedHourMinuteSecond_ / 10000 % 60;
      this.millisByTen_ = this.encodedHourMinuteSecond_ % 10000;
   }

   public int getEncodedHourMinuteSecond() {
      return this.encodedHourMinuteSecond_;
   }

   public int getEncodedYearMonthDay() {
      return this.encodedYearMonthDay_;
   }

   class InnerCalendar extends GregorianCalendar {
      InnerCalendar() {
      }

      InnerCalendar(int var2, int var3, int var4) {
         super(var2, var3, var4);
      }

      InnerCalendar(int var2, int var3, int var4, int var5, int var6, int var7) {
         super(var2, var3, var4, var5, var6, var7);
      }

      public long getTheTimeInMillis() {
         return super.getTimeInMillis();
      }
   }
}
