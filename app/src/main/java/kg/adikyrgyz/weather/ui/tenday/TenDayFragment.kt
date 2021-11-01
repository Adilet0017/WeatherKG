package kg.adikyrgyz.weather.ui.tenday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kg.adikyrgyz.weather.databinding.FragmentTenDayBinding
import kg.adikyrgyz.weather.ui.home.WeatherViewModel

class TenDayFragment : Fragment() {

    private var _binding: FragmentTenDayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTenDayBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = TenDayFragment()
    }
}