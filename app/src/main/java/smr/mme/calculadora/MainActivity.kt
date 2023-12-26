package smr.mme.calculadora


import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlin.math.absoluteValue



class MainActivity : AppCompatActivity() {



    private var but0: Button? = null
    private var but1: Button? = null
    private var but2: Button? = null
    private var but3: Button? = null
    private var but4: Button? = null
    private var but5: Button? = null
    private var but6: Button? = null
    private var but7: Button? = null
    private var but8: Button? = null
    private var but9: Button? = null

    private var butSuma: Button? = null
    private var butResta: Button? = null
    private var butProducto: Button? = null
    private var butDivision: Button? = null
    private var butModulo: Button? = null
    private var butProductoX2: Button? = null
    private var butProductoX1024: Button? = null
    private var butDivisionX2: Button? = null
    private var butDivisionX1024: Button? = null
    private var butSigno: Button? = null

    private var butDel: Button? = null
    private var butReset: Button? = null

    private var butAns: Button? = null

    private var butIgual: Button? = null

    private var lcd: TextView? = null

    private var num: Long = N0
    private var ans: Long = N0
    private var operador: Int = NOP
    private  var error: Boolean = false


    companion object {
        const val NOP = 0
        const val SUMA = 1
        const val RESTA = 2
        const val PRODUCTO = 3
        const val DIVISION = 4
        const val MODULO = 5
        const val PRODUCTO_X2 = 6
        const val PRODUCTO_X1024 = 7
        const val DIVISION_X2 = 8
        const val DIVISION_X1024 = 9
        const val CERO= "0"
        const val N0 =0L
        const val N2 = 2L
        const val N10 = 10L
        const val N2e10 = 1024L
        const val  NMAX = 999999999999999999L
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        WindowCompat.setDecorFitsSystemWindows(this.window, false)
        WindowInsetsControllerCompat(window, View(this)).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        this.but0 = this.findViewById(R.id.b0)
        this.but1 = this.findViewById(R.id.b1)
        this.but2 = this.findViewById(R.id.b2)
        this.but3 = this.findViewById(R.id.b3)
        this.but4 = this.findViewById(R.id.b4)
        this.but5 = this.findViewById(R.id.b5)
        this.but6 = this.findViewById(R.id.b6)
        this.but7 = this.findViewById(R.id.b7)
        this.but8 = this.findViewById(R.id.b8)
        this.but9 = this.findViewById(R.id.b9)

        this.butSuma = this.findViewById(R.id.bSum)
        this.butResta = this.findViewById(R.id.bRes)
        this.butProducto = this.findViewById(R.id.bPro)
        this.butDivision = this.findViewById(R.id.bDiv)
        this.butModulo = this.findViewById(R.id.bMod)
        this.butProductoX2 = this.findViewById(R.id.bx2)
        this.butProductoX1024 = this.findViewById(R.id.bx1024)
        this.butDivisionX2 = this.findViewById(R.id.bmit)
        this.butDivisionX1024 = this.findViewById(R.id.bDiv1024)
        this.butSigno = this.findViewById(R.id.bneg)

        this.butDel = this.findViewById(R.id.bDEL)
        this.butReset = this.findViewById(R.id.bReset)

        this.butAns = this.findViewById(R.id.bAns)

        this.butIgual = this.findViewById(R.id.beq)

        this.lcd = this.findViewById(R.id.LCD)
        this.lcd?.text = CERO

        this.but0?.setOnClickListener { botonNum("0") }
        this.but1?.setOnClickListener { botonNum("1") }
        this.but2?.setOnClickListener { botonNum("2") }
        this.but3?.setOnClickListener { botonNum("3") }
        this.but4?.setOnClickListener { botonNum("4") }
        this.but5?.setOnClickListener { botonNum("5") }
        this.but6?.setOnClickListener { botonNum("6") }
        this.but7?.setOnClickListener { botonNum("7") }
        this.but8?.setOnClickListener { botonNum("8") }
        this.but9?.setOnClickListener { botonNum("9") }

        this.butSuma?.setOnClickListener { botonOpB(SUMA) }
        this.butResta?.setOnClickListener { botonOpB(RESTA) }
        this.butProducto?.setOnClickListener { botonOpB(PRODUCTO) }
        this.butDivision?.setOnClickListener { botonOpB(DIVISION) }
        this.butModulo?.setOnClickListener { botonOpB(MODULO) }
        this.butProductoX2?.setOnClickListener { botonOpM(PRODUCTO_X2) }
        this.butProductoX1024?.setOnClickListener { botonOpM(PRODUCTO_X1024) }
        this.butDivisionX2?.setOnClickListener { botonOpM(DIVISION_X2) }
        this.butDivisionX1024?.setOnClickListener { botonOpM(DIVISION_X1024) }
        this.butSigno?.setOnClickListener { botonSigno() }
        this.butDel?.setOnClickListener { botonDel() }
        this.butReset?.setOnClickListener { botonReset() }
        this.butAns?.setOnClickListener { botonAns() }
        this.butIgual?.setOnClickListener { botonIgual() }
    }
    private fun botonNum(digit: String) {
        if(this.error) return
        if( this.num == N0 )
        {
            this.lcd?.text = digit
            this.num=digit.toLong()
        }else {
            if (this.isOverflow(this.lcd?.text.toString().toLong()*10)) return
            this.num = (this.lcd?.text.toString() + digit).toLong()
            this.lcd?.text = this.num.toString()
        }
    }

    private fun botonOpB(op: Int) {
        if(this.error)  return
        this.ans = this.lcd?.text.toString().toLong()
        this.operador = op
        this.num = N0
    }

    private fun botonDel() {
        if(this.error) {this.botonReset();  return}
        if (this.num.absoluteValue  >= N10) {
                this.lcd?.text = this.lcd?.text.toString().dropLast(1)
                this.num = this.lcd?.text.toString().toLong()
        } else
        {
                this.lcd?.text=CERO
                this.num = N0
        }
    }

    private fun botonReset() {
        this.error=false
        this.lcd?.gravity = Gravity.END
        this.lcd?.text = CERO
        this.operador = NOP
        this.num = N0
        this.ans = N0
    }

    private fun botonAns() {
        if(this.error) return
        this.lcd?.text = ans.toString()
        this.num=ans
    }


    private fun botonIgual() {
        if(this.error) return
        this.num = this.lcd?.text.toString().toLong()
        if ( this.isDiv0() ) {
            this.lcd?.text = this.getString(R.string.div0)
            this.lcd?.gravity = Gravity.CENTER_HORIZONTAL
            this.error=true
            return
        }
        val resultado = when (this.operador) {
                SUMA -> this.ans + this.num
                RESTA -> this.ans - this.num
                PRODUCTO -> this.ans * this.num
                DIVISION -> this.ans / this.num
                MODULO -> this.ans % this.num
                else -> this.num
        }
        if(this.isOverflow(resultado)){
            this.lcd?.text = this.getString(R.string.overflow)
            this.lcd?.gravity = Gravity.CENTER_HORIZONTAL
            this.error=true
        } else  this.lcd?.text = resultado.toString()
    }

    private fun botonOpM(op: Int) {
        if(this.error) return
        this.num = this.lcd?.text.toString().toLong()
        val resultado: Long = when (op) {
            PRODUCTO_X2 -> this.num * N2
            PRODUCTO_X1024 -> this.num * N2e10
            DIVISION_X2 -> this.num / N2
            DIVISION_X1024 -> this.num / N2e10
            else -> this.num
        }
        if(this.isOverflow(resultado)){
            this.lcd?.text = this.getString(R.string.overflow)
            this.lcd?.gravity = Gravity.CENTER_HORIZONTAL
            this.error=true
        } else  {
            this.lcd?.text = resultado.toString()
            this.ans = this.num
        }
    }
    private fun botonSigno() {
        if(this.error) return
        this.num = this.lcd?.text.toString().toLong()
        this.num = (- this.num)
        this.lcd?.text =this.num.toString()

    }
    private fun isDiv0() : Boolean {
        return ((this.operador == DIVISION) && (this.num == N0))
    }
   private fun isOverflow(num : Long) : Boolean {
       return (num.absoluteValue > NMAX)
   }
}
