;; ------------------------------
;; ---------- Euler -------------
;; ------------------------------
(use 'clojure.contrib.math)

;;
;; ---------- Euler 1 -------------
;;
(defn divideableByAny? [a b]
  "Checks if a is dividable by any num in b"
  (true? (some #(zero? (mod a %)) 
	       b)))

;; Command:
;; (reduce + (filter #(divideableByAny? % [3 5]) (range 3 1000)))

;;
;; ---------- Euler 2 -------------
;;
;; From "Programming Clojure"
(defn fibo []
  "Returns a lazy fibonacci seq"
  (map first (iterate (fn [[a b]] [b (+ a b)]) [0 1])))

;; Command:
;; (reduce + (filter even? (take-while #(< % 4000000) (fibo))))


;;
;; ---------- Euler 3 -------------
;;
(defn divideableByAll? [a b]
  "Checks if a is divideable by all nums in b"
  (every? #(zero? (mod a %)) 
	  b))

(defn naturalNumbers []
  "Return a lazy-seq of all natural nums"
  (iterate inc 1))	  

;; Command:
;; (loop [r 600851475143 nums (rest (naturalNumbers))]
;;   (if (= r 1)
;;     (first nums)
;;     (if (divideableByAll? r [(first nums)])
;;       (recur (/ r (first nums)) nums)
;;       (recur r (rest nums)))))


;;
;; ---------- Euler 4 -------------
;;
(defn palindrom?
  "Checks if the given string is a palindrom or not"
  ([] true)
  ([pal] (if (= (first pal) (last pal))
	   (if (empty? pal)
	     true
	     (recur (apply str (drop-last (rest pal)))))
	   false)))

(defn numVec [a b] 
  "Return a list of numeric vectors like [1 1] [1 2] .. [a b]"
  (for [x (range 1 (inc a)), y (range 1 (inc b))] [x y]))

;; Command:
;; (last (sort (filter #(palindrom? (str %)) (map #(* (% 0) (% 1)) (numVec 999 999)))))


;;
;; ---------- Euler 5 -------------
;;
;; Brute force .. took some time ..
;; Command:
;; (time (first (drop-while #(not (divideableByAll? % (range 11 20))) (naturalNumbers))))
;; -> "Elapsed time: 941008.48385 msecs"


;;
;; ---------- Euler 6 -------------
(defn square
  "Calculates the square of x"
  ([] 0)
  ([x] (* x x)))

(defn sum-vec [nums]
  (reduce + nums))

;; Command:
;; (- (square (sum-vec (range 1 101))) 
;;    (sum-vec (map square (range 1 101))))


;;
;; ---------- Euler 7 -------------
;;
(use 'clojure.contrib.lazy-seqs)

;; Command:
;; (last (take 10001 primes))


;;
;; ---------- Euler 8 -------------
;;
(defn str-to-num [s]
  (try
   (Integer/valueOf s)
   (catch NumberFormatException _ 
     (BigInteger. s))))

(defn char-seq-to-num [s]
  (str-to-num (apply str s)))

(defn char-seq-to-num-seq [s]
  (map #(str-to-num (str %)) s))

(defn maxseq [s] 
  (reduce max s))

;; Command:
;;(maxseq
;; (map #(reduce * (char-seq-to-num-seq %)) 
;;       (partition 5 1 "7316717653133062491922511967442657474235534919493496983520312774506326239578318016984801869478851843858615607891129494954595017379583319528532088055111254069874715852386305071569329096329522744304355766896648950445244523161731856403098711121722383113622298934233803081353362766142828064444866452387493035890729629049156044077239071381051585930796086670172427121883998797908792274921901699720888093776657273330010533678812202354218097512545405947522435258490771167055601360483958644670632441572215539753697817977846174064955149290862569321978468622482839722413756570560574902614079729686524145351004748216637048440319989000889524345065854122758866688116427171479924442928230863465674813919123162824586178664583591245665294765456828489128831426076900422421902267105562632111110937054421750694165896040807198403850962455444362981230987879927244284909188845801561660979191338754992005240636899125607176060588611646710940507754100225698315520005593572972571636269561882670428252483600823257530420752963450")))


;;
;; ---------- Euler 9 -------------
;;
;; Command:
;; (let [v (first (filter 
;;		#(let [a (% 0), b (% 1), c (- 1000 a b)]
;;		   (= (+ (expt a 2) (expt b 2)) (expt c 2)))
;;		(for [a (range 1 333) b (range 1 500)] [a b])))]
;;  (let [a (v 0), b (v 1), c (- 1000 a b)]
;;    (* a b c)))


;;
;; ---------- Euler 10 -------------
;;
;; Primes
;; http://paste.lisp.org/display/69952
(defn sieve [n]
  (let [n (int n)]
    "Returns a list of all primes from 2 to n"
    (let [root (int (Math/round (Math/floor (Math/sqrt n))))]
      (loop [i (int 3)
             a (int-array n)
             result (list 2)]
        (if (>= i n)
          (reverse result)
          (recur (+ i (int 2))
                 (if (< i root)
                   (loop [arr a
                          inc (+ i i)
                          j (* i i)]
                     (if (>= j n)
                       arr
                       (recur (do (aset arr j (int 1)) arr)
                              inc
                              (+ j inc))))
                   a)
                 (if (zero? (aget a i))
                   (conj result i)
                   result)))))))

;; Command:
;; (reduce + (sieve 2000000))


;;
;; ---------- Euler 13 -------------
;;

(defn digits [x] 
  (char-seq-to-num-seq (str x)))

;; Command:
;; (take 10 (digits (sum-vec (map char-seq-to-num
;;	      (partition 50 "37107287533902102798797998220837590246510135740250463769376774900097126481248969700780504170182605387432498619952474105947423330951305812372661730962991942213363574161572522430563301811072406154908250230675882075393461711719803104210475137780632466768926167069662363382013637841838368417873436172675728112879812849979408065481931592621691275889832738442742289174325203219235894228767964876702721893184745144573600130643909116721685684458871160315327670386486105843025439939619828917593665686757934951621764571418565606295021572231965867550793241933316490635246274190492910143244581382266334794475817892575867718337217661963751590579239728245598838407582035653253593990084026335689488301894586282278288018119938482628201427819413994056758715117009439035398664372827112653829987240784473053190104293586865155060062958648615320752733719591914205172558297169388870771546649911559348760353292171497005693854370070576826684624621495650076471787294438377604532826541087568284431911906346940378552177792951453612327252500029607107508256381565671088525835072145876576172410976447339110607218265236877223636045174237069058518606604482076212098132878607339694128114266041808683061932846081119106155694051268969251934325451728388641918047049293215058642563049483624672216484350762017279180399446930047329563406911573244438690812579451408905770622942919710792820955037687525678773091862540744969844508330393682126183363848253301546861961243487676812975343759465158038628759287849020152168555482871720121925776695478182833757993103614740356856449095527097864797581167263201004368978425535399209318374414978068609844840309812907779179908821879532736447567559084803087086987551392711854517078544161852424320693150332599594068957565367821070749269665376763262354472106979395067965269474259770973916669376304263398708541052684708299085211399427365734116182760315001271653786073615010808570091499395125570281987460043753582903531743471732693212357815498262974255273730794953759765105305946966067683156574377167401875275889028025717332296191766687138199318110487701902712526768027607800301367868099252546340106163286652636270218540497705585629946580636237993140746255962240744869082311749777923654662572469233228109171419143028819710328859780666976089293863828502533340334413065578016127815921815005561868836468420090470230530811728164304876237919698424872550366387845831148769693215490281042402013833512446218144177347063783299490636259666498587618221225225512486764533677201869716985443124195724099139590089523100588229554825530026352078153229679624948164195386821877476085327132285723110424803456124867697064507995236377742425354112916842768655389262050249103265729672370191327572567528565324825826546309220705859652229798860272258331913126375147341994889534765745501184957014548792889848568277260777137214037988797153829820378303147352772158034814451349137322665138134829543829199918180278916522431027392251122869539409579530664052326325380441000596549391598795936352974615218550237130764225512118369380358038858490341698116222072977186158236678424689157993532961922624679571944012690438771072750481023908955235974572318970677254791506150550495392297953090112996751986188088225875314529584099251203829009407770775672113067397083047244838165338735023408456470580773088295917476714036319800818712901187549131054712658197623331044818386269515456334926366572897563400500428462801835170705278318394258821455212272512503275512160354698120058176216521282765275169129689778932238195734329339946437501907836945765883352399886755061649651847751807381688378610915273579297013376217784275219262340194239963916804498399317331273132924185707147349566916674687634660915035914677504995186714302352196288948901024233251169136196266227326746080059154747183079839286853520694694454072476841822524674417161514036427982273348055556214818971426179103425986472045168939894221798260880768528778364618279934631376775430780936333301898264209010848802521674670883215120185883543223812876952786713296124747824645386369930090493103636197638780396218407357239979422340623539380833965132740801111666627891981488087797941876876144230030984490851411606618262936828367647447792391803351109890697907148578694408955299065364044742557608365997664579509666024396409905389607120198219976047599490197230297649139826800329731560371200413779037855660850892521673093931987275027546890690370753941304265231501194809377245048795150954100921645863754710598436791786391670211874924319957006419179697775990283006991536871371193661495281130587638027841075444973307840789923115535562561142322423255033685442488917353448899115014406480203690680639606723221932041495354150312888033953605329934036800697771065056663195481234880673210146739058568557934581403627822703280826165707739483275922328459417065250945123252306082291880205877731971983945018088807242966198081119777158542502016545090413245809786882778948721859617721078384350691861554356628840622574736922845095162084960398013400172393067166682355524525280460972253503534226472524250874054075591789781264330331690")))))


;;
;; ---------- Euler 14 -------------
;;
(defn collatz
  "Calculates the length of the collatz sequence for the given number"
  ([n] (collatz n 1))
  ([n c] (if (= n 1)
	  c
	  (if (even? n)
	    (recur (/ n 2) (inc c))
	    (recur (+ (* n 3) 1) (inc c))))))

(defn find-greatest
  "Finds the greatest return value of invoking f with the values of the given vec"
  ([f [val & r]] (find-greatest f r val (f val)))
  ([f r in out] 
     (if (empty? r)
       {:value in :return out}
       (let [p (first r) col (f p)]
	 (if (> col out)
	   (recur f (rest r) p col)
	   (recur f (rest r) in out))))))

;; Command:
;; (time (find-greatest collatz (range 1 1000000)))
;; "Elapsed time: 46599.12235 msecs"


;;
;; ---------- Euler 15 -------------
;;
(declare mem-cube-paths)

(defn cube-paths [n j]
  (if (or (< n 0) (< j 0))
    0
    (let [l (mem-cube-sum (dec n) j) 
	  t (mem-cube-sum n (dec j))]
      (cond (and (zero? l) (zero? t)) 1
	    (zero? l) t
	    (zero? t) l
	    :else (+ l t)))))

(def mem-cube-paths (memoize cube-sum))

;; Command:
;; (cube-paths 20 20)
;; There is a much simpler solution using pascal's triangle


;;
;; ---------- Euler 16 -------------
;;
;; Command:
;; (sum-vec (digits (expt 2 1000)))


;;
;; ---------- Euler 18 -------------
;;
(def small-triangle [
[3]
[7 4]
[2 4 6]
[8 5 9 3]])

(def triangle  [
[75]
[95 64]
[17 47 82]
[18 35 87 10]
[20  4 82 47 65]
[19  1 23 75  3 34]
[88  2 77 73  7 63 67]
[99 65  4 28  6 16 70 92]
[41 41 26 56 83 40 80 70 33]
[41 48 72 33 47 32 37 16 94 29]
[53 71 44 65 25 43 91 52 97 51 14]
[70 11 33 28 77 73 17 78 39 68 17 57]
[91 71 52 38 17 14 91 43 58 50 27 29 48]
[63 66  4 68 89 53 67 30 73 16 69 87 40 31]
[ 4 62 98 27 23  9 70 98 73 93 38 53 60 4 23]])

(defn anc-sum [triangle j n]
  (let [v (nth (nth triangle j []) n 0)
	row (nth triangle (inc j) [])]
    (if (empty? row)
      v
      (+ v (max (anc-sum triangle (inc j) n)
		(anc-sum triangle (inc j) (dec n)))))))

(defn max-sum-triangle [triangle]
	(when triangle
	  (let [rev (reverse triangle)
		size (count triangle)]
	    (->> (range 0 size)
		 (map #(anc-sum rev 0 %))
		 (reduce max)))))

;; Command:
;; (mem-max-sum-triangle triangle)

;; Memoized version!

(declare mem-anc-sum)
(defn anc-sum2 [triangle j n]
  (let [v (nth (nth triangle j []) n 0)
	row (nth triangle (inc j) [])]
    (if (empty? row)
      v
      (+ v (max (mem-anc-sum triangle (inc j) n)
		(mem-anc-sum triangle (inc j) (dec n)))))))

(def mem-anc-sum (memoize anc-sum2))

(defn mem-max-sum-triangle [triangle]
	(when triangle
	  (let [rev (reverse triangle)
		size (count triangle)]
	    (->> (range 0 size)
		 (map #(mem-anc-sum rev 0 %))
		 (reduce max)))))

;; Comparison

;; user> (dotimes [i 5]
;; 	 	   (time (max-sum-triangle triangle)))
;; "Elapsed time: 709.331294 msecs"
;; "Elapsed time: 740.759319 msecs"
;; "Elapsed time: 740.058476 msecs"
;; "Elapsed time: 741.694414 msecs"
;; "Elapsed time: 713.760037 msecs"

;; user> (dotimes [i 5]
;;	 	   (time (mem-max-sum-triangle triangle)))
;; "Elapsed time: 1.760809 msecs"
;; "Elapsed time: 0.18123 msecs"
;; "Elapsed time: 0.09466 msecs"
;; "Elapsed time: 0.088514 msecs"
;; "Elapsed time: 0.093967 msecs"


;;
;; ---------- Euler 20 -------------
;;
(declare fac)
(defn factorial [a]
  (if (= a 1)
    1
    (* a (fac (dec a)))))

(def fac (memoize factorial))

;; Command:
;; (sum-vec (digits (fac 100)))

;;
;; ---------- Euler 21 -------------
;;
(defn dividers [a]
  (for [n (range 1 (inc (/ a 2))) :when (zero? (mod a n))]
    n))

(defn sum-dividers [a]
  (sum-vec (dividers a)))

(def mem-sum-dividers 
     (memoize sum-dividers))

(defn amicable? [a]
  (let [b (mem-sum-dividers a)]
    (and (not= a b) 
	 (= a (mem-sum-dividers b)))))

;; Command:
;; (sum-vec (filter amicable? (range 1 10001)))


;;
;; ---------- Euler 25 -------------
;;
(defn fibterm [nth fib]
  (let [n (first fib)]
    (if (= (count (digits n)) 1000)
      nth
      (recur (inc nth) (rest fib)))))

;; Command:
;; (fibterm 0 (fibo))


;;
;; ---------- Euler 48 -------------
;;
;; Command:
;; (take-last 10 (digits (sum-vec (map #(expt % %) (range 1 1000)))))


;;
;; ---------- Euler 67 -------------
;;
(use 'clojure.contrib.duck-streams)
(use 'clojure.contrib.str-utils)

(defn str-seq-to-num-seq [s]
  (->> s
       (re-split #" ")
       (map str-to-num)))

(def big-triangle (map str-seq-to-num-seq (read-lines "triangle.txt")))

;; Command:
;; (mem-max-sum-triangle big-triangle)
