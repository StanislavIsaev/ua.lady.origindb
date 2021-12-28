package interbase.interclient;

import java.util.NoSuchElementException;

final class EscapeLexer {
   private int currentPos_;
   private int maxPos_;
   private String lexStr_;
   private String delimiters_;
   private boolean inSingleQuotedString_;
   private boolean inDoubleQuotedString_;
   private int curlyNestingDepth_;
   private char currentChar_;
   private char prevChar_;

   EscapeLexer(String var1) {
      this(var1, " \n\t\r'\"{},()\\[]");
   }

   EscapeLexer(String var1, String var2) {
      this.curlyNestingDepth_ = 0;
      this.currentPos_ = 0;
      this.maxPos_ = var1.length() - 1;
      this.lexStr_ = var1;
      this.prevChar_ = 0;
      this.currentChar_ = var1.charAt(this.currentPos_);
      this.delimiters_ = var2;
      if (this.maxPos_ >= 0) {
         this.inSingleQuotedString_ = this.currentChar_ == '\'';
         this.inDoubleQuotedString_ = this.currentChar_ == '"';
         if (this.currentChar_ == '{') {
            this.curlyNestingDepth_ = 1;
         } else {
            this.curlyNestingDepth_ = 0;
         }
      } else {
         this.inSingleQuotedString_ = false;
         this.inDoubleQuotedString_ = false;
         this.curlyNestingDepth_ = 0;
      }

   }

   private synchronized boolean advance() {
      ++this.currentPos_;
      if (this.currentPos_ > this.maxPos_) {
         return false;
      } else {
         this.prevChar_ = this.currentChar_;
         this.currentChar_ = this.lexStr_.charAt(this.currentPos_);
         if (this.currentChar_ == '\'' && (this.prevChar_ != '\\' || this.inSingleQuotedString_)) {
            this.inSingleQuotedString_ = !this.inSingleQuotedString_;
         }

         if (this.currentChar_ == '"' && this.prevChar_ != '\\') {
            this.inDoubleQuotedString_ = !this.inDoubleQuotedString_;
         }

         if (!this.inSingleQuotedString_ && !this.inDoubleQuotedString_) {
            if (this.currentChar_ == '{' && this.prevChar_ != '\\') {
               ++this.curlyNestingDepth_;
            }

            if (this.currentChar_ == '}' && this.prevChar_ != '\\' && this.curlyNestingDepth_ > 0) {
               --this.curlyNestingDepth_;
            }
         }

         return true;
      }
   }

   private boolean isWhiteSpace(char var1) {
      return var1 == ' ' || var1 == '\n' || var1 == '\t' || var1 == '\r';
   }

   private synchronized boolean skipWhiteSpaces() {
      do {
         if (!this.isWhiteSpace(this.currentChar_)) {
            return true;
         }
      } while(this.advance());

      return false;
   }

   private boolean isDelimChar(char var1) {
      for(int var2 = 0; var2 < this.delimiters_.length(); ++var2) {
         if (this.delimiters_.charAt(var2) == var1) {
            return true;
         }
      }

      return false;
   }

   private synchronized int findNext(char var1) {
      do {
         if (this.currentChar_ == var1) {
            int var2 = this.currentPos_;
            this.advance();
            return var2;
         }
      } while(this.advance());

      return -1;
   }

   synchronized int findNextNotInQuotedString(char var1) {
      do {
         if (this.currentChar_ == var1 && !this.inSingleQuotedString_ && !this.inDoubleQuotedString_) {
            int var2 = this.currentPos_;
            this.advance();
            return var2;
         }
      } while(this.advance());

      return -1;
   }

   synchronized int findNextMatching(char var1) {
      boolean var2 = this.inSingleQuotedString_;
      boolean var3 = this.inDoubleQuotedString_;
      int var4 = this.curlyNestingDepth_;

      do {
         if (this.currentChar_ == var1 && var2 == this.inSingleQuotedString_ && var3 == this.inDoubleQuotedString_ && var4 == this.curlyNestingDepth_ + 1 && var1 == '}') {
            int var5 = this.currentPos_;
            this.advance();
            return var5;
         }

         if (!this.advance()) {
            return -1;
         }
      } while((!var2 || this.inSingleQuotedString_) && (!var3 || this.inDoubleQuotedString_) && (var4 != this.curlyNestingDepth_ + 1 || this.currentChar_ != '}' || var1 == '}'));

      return -1;
   }

   synchronized String nextToken() throws NoSuchElementException {
      StringBuffer var1 = new StringBuffer();
      if (this.currentPos_ > this.maxPos_) {
         throw new NoSuchElementException();
      } else if (!this.skipWhiteSpaces()) {
         throw new NoSuchElementException();
      } else {
         var1.append(this.currentChar_);
         if (this.isDelimChar(this.currentChar_)) {
            this.advance();
            return var1.toString();
         } else {
            while(this.advance()) {
               if (this.isDelimChar(this.currentChar_)) {
                  return var1.toString();
               }

               var1.append(this.currentChar_);
            }

            return var1.toString();
         }
      }
   }

   synchronized String subString(int var1, int var2) throws StringIndexOutOfBoundsException {
      return this.lexStr_.substring(var1, var2);
   }

   synchronized String subString(int var1) throws StringIndexOutOfBoundsException {
      return this.lexStr_.substring(var1);
   }

   synchronized boolean hasMoreTokens() {
      return this.currentPos_ <= this.maxPos_ && this.skipWhiteSpaces();
   }
}
